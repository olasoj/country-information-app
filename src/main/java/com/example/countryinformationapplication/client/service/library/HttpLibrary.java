package com.example.countryinformationapplication.client.service.library;

import com.example.countryinformationapplication.client.model.RequestProperties;
import com.example.countryinformationapplication.client.model.ResponseProperties;

import java.io.Serializable;

public interface HttpLibrary {
    <T extends Serializable, U> ResponseProperties<U> exchange(RequestProperties<T> build, Class<U> responseClassType);
}
