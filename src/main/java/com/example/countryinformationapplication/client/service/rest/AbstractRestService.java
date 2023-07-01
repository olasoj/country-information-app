package com.example.countryinformationapplication.client.service.rest;

import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpException;
import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpResourceTimeoutException;
import com.example.countryinformationapplication.client.model.RequestProperties;
import com.example.countryinformationapplication.client.model.ResponseProperties;
import com.example.countryinformationapplication.client.service.library.HttpLibrary;

import java.io.Serializable;

public abstract class AbstractRestService implements RestService {
    private final HttpLibrary httpLibrary;

    protected AbstractRestService(HttpLibrary httpLibrary) {
        this.httpLibrary = httpLibrary;
    }

    @Override
    public <T extends Serializable, U> ResponseProperties<U> request(RequestProperties<T> requestProperties, Class<U> responseClassType) throws CountryInformationApplicationHttpException, CountryInformationApplicationHttpResourceTimeoutException {
        return httpLibrary.exchange(requestProperties, responseClassType);
    }

}
