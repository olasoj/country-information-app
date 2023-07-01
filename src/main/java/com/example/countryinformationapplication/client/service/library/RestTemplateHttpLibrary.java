package com.example.countryinformationapplication.client.service.library;

import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpException;
import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpResourceTimeoutException;
import com.example.countryinformationapplication.client.model.internal.valueobject.RequestProperties;
import com.example.countryinformationapplication.client.model.internal.valueobject.ResponseProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.net.URISyntaxException;

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
    public <T extends Serializable, U> ResponseProperties<U> exchange(RequestProperties<T> build, Class<U> responseClassType) throws CountryInformationApplicationHttpException, CountryInformationApplicationHttpResourceTimeoutException {
        try {
            ResponseEntity<String> responseEntity = exchange(build);
            return parseResponse(responseClassType, responseEntity);
        } catch (RestClientResponseException restClientResponseException) {
            ResponseProperties<String> responseProperties = toResponseProperties(restClientResponseException);
            throw new CountryInformationApplicationHttpException(restClientResponseException.getMessage(), restClientResponseException.getCause(), responseProperties);
        } catch (ResourceAccessException resourceAccessException) {
            throw new CountryInformationApplicationHttpResourceTimeoutException(resourceAccessException.getMessage(), resourceAccessException.getCause());
        }
    }

    private <T extends Serializable> ResponseEntity<String> exchange(RequestProperties<T> requestProperties) throws ResourceAccessException, RestClientResponseException {
        try {
            logReqRes(requestProperties);
            URI url = new URI(requestProperties.getUrl());
            HttpEntity<T> httpEntity = new HttpEntity<>(requestProperties.getBody(), requestProperties.getHttpHeaders());
            return restTemplate.exchange(url, requestProperties.getHttpMethod(), httpEntity, String.class);
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Malformed URI");
        } catch (UnknownContentTypeException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, RESPONSE_MESSAGE);
        }
    }

    private <U> ResponseProperties<U> parseResponse(Class<U> responseClassType, ResponseEntity<String> responseEntity) {
        try {
            logReqRes(responseClassType);

            ResponseProperties.ResponsePropertiesBuilder<U> responsePropertiesBuilder = ResponseProperties.builder();
            U responseBody = objectMapper.readValue(responseEntity.getBody(), responseClassType);

            logReqRes(responseBody);
            return responsePropertiesBuilder
                    .httpHeaders(responseEntity.getHeaders())
                    .httpStatus(responseEntity.getStatusCode())
                    .body(responseBody)
                    .build();
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to parse response");
        }
    }

    protected <T> void logReqRes(T reqResBody) {
        try {
            String prettyString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqResBody);
            LOGGER.info(prettyString);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to log  request/response body");
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
