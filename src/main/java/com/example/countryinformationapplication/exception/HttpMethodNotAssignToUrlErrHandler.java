package com.example.countryinformationapplication.exception;

import com.example.countryinformationapplication.util.response.ResponseModel;
import com.example.countryinformationapplication.util.response.model.Response;
import com.example.countryinformationapplication.util.response.model.ResponseError;
import com.example.countryinformationapplication.util.response.transformer.ResponseAssembler;
import com.example.countryinformationapplication.util.response.transformer.ResponseErrorAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class HttpMethodNotAssignToUrlErrHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpMethodNotAssignToUrlErrHandler.class);
    private final ResponseModel responseModel;

    public HttpMethodNotAssignToUrlErrHandler(@Qualifier("responseModel") ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public void handleNoHandlerFoundException(HttpServletRequest request, HttpServletResponse response, NoHandlerFoundException ex) {
        var message = String.format("Could not find the %s method for URL: %s", ex.getHttpMethod(), ex.getRequestURL());
        LOGGER.error(ex.getMessage(), ex);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(message, HttpStatus.NOT_FOUND);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.NOT_FOUND, responseError);
        responseModel.writeResponse(response, errorResponse);
    }
}