package com.example.countryinformationapplication.client.service.library;

import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpResourceTimeoutException;
import com.example.countryinformationapplication.client.exception.CountryInformationApplicationResponseStatusException;
import com.example.countryinformationapplication.client.model.RequestProperties;
import com.example.countryinformationapplication.client.model.ResponseProperties;
import com.example.countryinformationapplication.util.JacksonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.net.URI;

@Service
public class RestTemplateHttpLibrary implements HttpLibrary {

    protected static final String RESPONSE_MESSAGE = "Service is currently unavailable, please try later";
    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateHttpLibrary.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    protected RestTemplateHttpLibrary(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T extends Serializable, U> ResponseProperties<U> exchange(RequestProperties<T> build, Class<U> responseClassType) {
        try {
            ResponseEntity<String> responseEntity = exchange(build);
            return parseResponse(responseClassType, responseEntity);
        } catch (RestClientResponseException restClientResponseException) {
            ResponseProperties<String> responseProperties = toResponseProperties(restClientResponseException);
            throw new CountryInformationApplicationResponseStatusException(restClientResponseException.getMessage(), restClientResponseException.getCause(), responseProperties);
        } catch (ResourceAccessException resourceAccessException) {
            throw new CountryInformationApplicationHttpResourceTimeoutException(resourceAccessException.getMessage(), resourceAccessException.getCause());
        }
    }

    private <T extends Serializable> ResponseEntity<String> exchange(RequestProperties<T> requestProperties) throws ResourceAccessException, RestClientResponseException {
        try {
            JacksonUtil.logReqRes(requestProperties);
            URI url = requestProperties.getUrl();
            HttpEntity<T> httpEntity = new HttpEntity<>(requestProperties.getBody(), requestProperties.getHttpHeaders());
            return restTemplate.exchange(url, requestProperties.getHttpMethod(), httpEntity, String.class);
        } catch (UnknownContentTypeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, RESPONSE_MESSAGE, e);
        }
    }

    private <U> ResponseProperties<U> parseResponse(Class<U> responseClassType, ResponseEntity<String> responseEntity) {
        try {
            JacksonUtil.logReqRes(responseClassType);

            ResponseProperties.ResponsePropertiesBuilder<U> responsePropertiesBuilder = ResponseProperties.builder();
            U responseBody = objectMapper.convertValue(responseEntity.getBody(), responseClassType);

            JacksonUtil.logReqRes(responseBody);
            return responsePropertiesBuilder
                    .httpHeaders(responseEntity.getHeaders())
                    .httpStatus(responseEntity.getStatusCode())
                    .body(responseBody)
                    .build();
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to parse response");
        }
    }

    private ResponseProperties<String> toResponseProperties(RestClientResponseException e) {
        ResponseProperties.ResponsePropertiesBuilder<String> builder = ResponseProperties.builder();
        return builder
                .httpHeaders(e.getResponseHeaders())
                .httpStatus(HttpStatus.valueOf(e.getRawStatusCode()))
                .body(e.getResponseBodyAsString())
                .build();
    }
}
