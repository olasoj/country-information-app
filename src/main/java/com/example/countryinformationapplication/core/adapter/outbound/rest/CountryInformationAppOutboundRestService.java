package com.example.countryinformationapplication.core.adapter.outbound.rest;

import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpException;
import com.example.countryinformationapplication.client.exception.CountryInformationApplicationResponseStatusException;
import com.example.countryinformationapplication.client.model.RequestProperties;
import com.example.countryinformationapplication.client.model.ResponseProperties;
import com.example.countryinformationapplication.client.service.rest.RestService;
import com.example.countryinformationapplication.core.model.outbound.CountryInformationAppOutboundRestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.Objects;

@Service
public class CountryInformationAppOutboundRestService implements RestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryInformationAppOutboundRestService.class);

    private final RestService restService;
    private final ObjectMapper objectMapper;

    protected CountryInformationAppOutboundRestService(@Qualifier("restTemplateRestService") RestService restService, @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.restService = restService;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T extends Serializable, U> ResponseProperties<U> request(RequestProperties<T> requestProperties, Class<U> responseClassType) throws CountryInformationApplicationHttpException {

        ResponseProperties<String> responseProperties = restService.request(requestProperties, String.class);
        JsonNode responseBody = getResponseBody(responseProperties);

        CountryInformationAppOutboundRestResponse countryInformationAppOutboundRestResponse = objectMapper.convertValue(responseBody, new TypeReference<>() {
        });

        if (Objects.isNull(countryInformationAppOutboundRestResponse) || Boolean.TRUE.equals(countryInformationAppOutboundRestResponse.getError()))
            throw new CountryInformationApplicationResponseStatusException(
                    Objects.isNull(countryInformationAppOutboundRestResponse) ? "An error occurred" : countryInformationAppOutboundRestResponse.getMsg(),
                    responseProperties
            );

        if (!responseBody.has("data"))
            throw new CountryInformationApplicationResponseStatusException(
                    "Malformed response body",
                    responseProperties
            );

        ResponseProperties.ResponsePropertiesBuilder<U> responsePropertiesBuilder = ResponseProperties.builder();

        JsonNode responseResult = (responseBody.has("data")) ? responseBody.get("data")
                : JsonNodeFactory.instance.objectNode();

        U expectedResponseBody = objectMapper.convertValue(responseResult, responseClassType);

        return responsePropertiesBuilder
                .httpHeaders(responseProperties.getHttpHeaders())
                .httpStatus(responseProperties.getHttpStatus())
                .body(expectedResponseBody)
                .build();

    }

    private JsonNode getResponseBody(ResponseProperties<String> responseProperties) {
        try {
            return objectMapper.readValue(responseProperties.getBody(), JsonNode.class);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get body");
        }
    }
}
