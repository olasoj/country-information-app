package com.example.countryinformationapplication.client.service.rest;

import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpException;
import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpResourceTimeoutException;
import com.example.countryinformationapplication.client.model.internal.valueobject.RequestProperties;
import com.example.countryinformationapplication.client.model.internal.valueobject.ResponseProperties;

import java.io.Serializable;

public interface RestService {
    <T extends Serializable, U> ResponseProperties<U> request(RequestProperties<T> build, Class<U> responseClassType) throws CountryInformationApplicationHttpException, CountryInformationApplicationHttpResourceTimeoutException;

}
