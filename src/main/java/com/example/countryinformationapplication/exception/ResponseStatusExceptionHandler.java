package com.example.countryinformationapplication.exception;

import com.example.countryinformationapplication.util.response.ResponseModel;
import com.example.countryinformationapplication.util.response.model.Response;
import com.example.countryinformationapplication.util.response.model.ResponseError;
import com.example.countryinformationapplication.util.response.transformer.ResponseAssembler;
import com.example.countryinformationapplication.util.response.transformer.ResponseErrorAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseStatusExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseStatusExceptionHandler.class);
    private final ResponseModel responseModel;

    public ResponseStatusExceptionHandler(@Qualifier("responseModel") ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public void handleResponseStatusException(HttpServletRequest request, HttpServletResponse response, ResponseStatusException ex) {
        LOGGER.error(ex.getMessage(), ex);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(ex.getReason(), ex.getStatus());
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(ex.getStatus(), responseError);
        responseModel.writeResponse(response, errorResponse);
    }
}