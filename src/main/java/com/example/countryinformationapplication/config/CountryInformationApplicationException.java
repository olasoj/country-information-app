package com.example.countryinformationapplication.config;


public class CountryInformationApplicationException extends RuntimeException {

    public CountryInformationApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CountryInformationApplicationException(String message) {
        super(message);
    }
}
