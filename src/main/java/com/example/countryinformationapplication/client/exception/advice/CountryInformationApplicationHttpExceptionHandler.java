package com.example.countryinformationapplication.client.exception.advice;


import com.example.countryinformationapplication.client.exception.CountryInformationApplicationResponseStatusException;
import com.example.countryinformationapplication.client.model.ResponseProperties;
import com.example.countryinformationapplication.util.response.ResponseModel;
import com.example.countryinformationapplication.util.response.model.Response;
import com.example.countryinformationapplication.util.response.model.ResponseError;
import com.example.countryinformationapplication.util.response.transformer.ResponseAssembler;
import com.example.countryinformationapplication.util.response.transformer.ResponseErrorAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CountryInformationApplicationHttpExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryInformationApplicationHttpExceptionHandler.class);
    private final ResponseModel responseModel;

    public CountryInformationApplicationHttpExceptionHandler(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    @ExceptionHandler(CountryInformationApplicationResponseStatusException.class)
    public void handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response, CountryInformationApplicationResponseStatusException countryInformationApplicationHttpErrorCodeException) {
        LOGGER.error(countryInformationApplicationHttpErrorCodeException.getMessage(), countryInformationApplicationHttpErrorCodeException);

        ResponseProperties<String> responseProperties = countryInformationApplicationHttpErrorCodeException.getResponseProperties();
        HttpStatus httpStatus = !(responseProperties.getHttpStatus().isError()) ? HttpStatus.INTERNAL_SERVER_ERROR : responseProperties.getHttpStatus();

        ResponseError responseError = ResponseErrorAssembler.toResponseError(countryInformationApplicationHttpErrorCodeException.getMessage(), httpStatus);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(httpStatus, responseError);
        responseModel.writeResponse(response, errorResponse);
    }

}
