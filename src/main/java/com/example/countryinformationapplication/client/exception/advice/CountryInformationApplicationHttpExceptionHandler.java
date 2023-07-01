package com.example.countryinformationapplication.client.exception.advice;


import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpException;
import com.example.countryinformationapplication.client.model.internal.valueobject.ResponseProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CountryInformationApplicationHttpExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryInformationApplicationHttpExceptionHandler.class);
    private final ObjectMapper objectMapper;

    public CountryInformationApplicationHttpExceptionHandler(@Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(CountryInformationApplicationHttpException.class)
    public void handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response, CountryInformationApplicationHttpException microInternalServicesHttpException) throws IOException {
        LOGGER.error(microInternalServicesHttpException.getMessage(), microInternalServicesHttpException);
        ResponseProperties<String> responseProperties = microInternalServicesHttpException.getResponseProperties();
        writeResponse(response, responseProperties);
    }

    private void writeResponse(HttpServletResponse response, ResponseProperties<String> responseProperties) throws IOException {
        response.setStatus(responseProperties.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=" + StandardCharsets.UTF_8.name());
        JsonNode jsonNode = objectMapper.readValue(responseProperties.getBody(), JsonNode.class);
        objectMapper.writeValue(response.getWriter(), jsonNode);
    }
}
