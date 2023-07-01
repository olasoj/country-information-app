package com.example.countryinformationapplication.client.exception;


import com.example.countryinformationapplication.config.CountryInformationApplicationException;

public class CountryInformationApplicationHttpException extends CountryInformationApplicationException {

    public CountryInformationApplicationHttpException(String message, Throwable cause) {
        super(message, cause);
    }
}
