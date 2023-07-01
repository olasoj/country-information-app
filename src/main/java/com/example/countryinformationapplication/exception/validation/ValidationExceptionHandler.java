package com.example.countryinformationapplication.exception.validation;


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
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionHandler.class);
    private final ResponseModel responseModel;

    public ValidationExceptionHandler(@Qualifier("responseModel") ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValid(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) {
        var errors = getErrors(ex);
        ResponseError responseError = ResponseErrorAssembler.toResponseError(errors, "Validation failed", HttpStatus.BAD_REQUEST);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.BAD_REQUEST, responseError);
        responseModel.writeResponse(response, errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationExceptionENotValid(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException ex) {
        var errors = getErrors(ex);
        ResponseError responseError = ResponseErrorAssembler.toResponseError(errors, "Validation failed", HttpStatus.BAD_REQUEST);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.BAD_REQUEST, responseError);
        responseModel.writeResponse(response, errorResponse);
    }

    private Map<String, Object> getErrors(ConstraintViolationException ex) {
        Map<String, Object> errors = new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        constraintViolations.forEach(
                constraintViolation -> errors.put(String.valueOf(constraintViolation.getPropertyPath()), constraintViolation.getMessage())
        );

        return errors;
    }

    private Map<String, Object> getErrors(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, Object>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException ex) {
        LOGGER.error(ex.getMessage(), ex);
        ResponseError responseError = ResponseErrorAssembler.toResponseError("No/Invalid Request body provided", HttpStatus.BAD_REQUEST);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.BAD_REQUEST, responseError);
        responseModel.writeResponse(response, errorResponse);
    }
}
