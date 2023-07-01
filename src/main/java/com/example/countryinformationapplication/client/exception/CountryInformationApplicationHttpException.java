package com.example.countryinformationapplication.client.exception;


import com.example.countryinformationapplication.client.model.internal.valueobject.ResponseProperties;
import com.example.countryinformationapplication.config.CountryInformationApplicationException;

public class CountryInformationApplicationHttpException extends CountryInformationApplicationException {

    private final ResponseProperties<String> responseProperties;

    public CountryInformationApplicationHttpException(String message, Throwable cause, ResponseProperties<String> responseProperties) {
        super(message, cause);
        this.responseProperties = responseProperties;
    }

    public ResponseProperties<String> getResponseProperties() {
        return responseProperties;
    }

}
