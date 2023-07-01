package com.example.countryinformationapplication.exception.filter;

import com.example.countryinformationapplication.util.response.ResponseModel;
import com.example.countryinformationapplication.util.response.model.Response;
import com.example.countryinformationapplication.util.response.model.ResponseError;
import com.example.countryinformationapplication.util.response.transformer.ResponseAssembler;
import com.example.countryinformationapplication.util.response.transformer.ResponseErrorAssembler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@ControllerAdvice
public class Err5XXFilter implements ResponseBodyAdvice<Object> {
    private final ResponseModel responseModel;

    public Err5XXFilter(@Qualifier("responseModel") ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {

        var res = ((ServletServerHttpResponse) response).getServletResponse();
        if (handleErr5XXErr(res)) return null;
        return body;
    }

    private boolean handleErr5XXErr(HttpServletResponse response) {
        assert !Objects.isNull(response);

        int status = response.getStatus();
        var httpStatus = HttpStatus.valueOf(status);
        if (status == 500) {
            ResponseError responseError = ResponseErrorAssembler.toResponseError("An internal server error occurred", httpStatus);
            Response<ResponseError> errorResponse = ResponseAssembler.toResponse(httpStatus, responseError);
            responseModel.writeResponse(response, errorResponse);
            return true;
        }
        return false;
    }
}
