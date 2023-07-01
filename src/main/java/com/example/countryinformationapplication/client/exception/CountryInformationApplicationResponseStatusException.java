package com.example.countryinformationapplication.client.exception;


import com.example.countryinformationapplication.client.model.ResponseProperties;
import com.example.countryinformationapplication.config.CountryInformationApplicationException;

public class CountryInformationApplicationResponseStatusException extends CountryInformationApplicationException {

    private final transient ResponseProperties<String> responseProperties;

    public CountryInformationApplicationResponseStatusException(String message, Throwable cause, ResponseProperties<String> responseProperties) {
        super(message, cause);
        this.responseProperties = responseProperties;
    }


    public CountryInformationApplicationResponseStatusException(String message, ResponseProperties<String> responseProperties) {
        super(message);
        this.responseProperties = responseProperties;
    }

    public ResponseProperties<String> getResponseProperties() {
        return responseProperties;
    }

}
