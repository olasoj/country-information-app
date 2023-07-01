package com.example.countryinformationapplication.client.exception;

import com.example.countryinformationapplication.config.CountryInformationApplicationException;

public class CountryInformationApplicationHttpResourceTimeoutException extends CountryInformationApplicationException {

    public CountryInformationApplicationHttpResourceTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
