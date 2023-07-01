package com.example.countryinformationapplication.core.adapter.outbound.rest;

import com.example.countryinformationapplication.client.model.internal.valueobject.RequestProperties;
import com.example.countryinformationapplication.client.model.internal.valueobject.ResponseProperties;
import com.example.countryinformationapplication.client.service.rest.RestService;
import com.example.countryinformationapplication.core.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class CountryInformationAppOutboundProxy {
    private static final String BASE_URL = "https://countriesnow.space/api/v0.1/countries";
    private final RestService restService;
    private final ObjectMapper objectMapper;

    public CountryInformationAppOutboundProxy(@Qualifier("countryInformationAppOutboundRestService") RestService restService, @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.restService = restService;
        this.objectMapper = objectMapper;
    }

    public PopulationOfCityOfCountry populationFilter() {

        String nigeria = "";
        String size = "";
        String requestURL = BASE_URL + "/population/cities/filter/q?country=" + nigeria + "&orderBy=population&order=descend&limit=" + size;

        RequestProperties<Serializable> individualUserRegisterRequestProperties = RequestProperties.builder()
                .uri(requestURL)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<PopulationOfCityOfCountry> baseIndividualUserResponseResponseProperties =
                restService.request(individualUserRegisterRequestProperties, PopulationOfCityOfCountry.class);

        PopulationOfCityOfCountry body = baseIndividualUserResponseResponseProperties.getBody();

        return objectMapper.convertValue(body.getData(), new TypeReference<>() {
        });
    }

    public PopulationOfCountryData populationFilterByCountry(String country) {

        String requestURL = BASE_URL + "/population/q?country=" + country;

        RequestProperties<Serializable> individualUserRegisterRequestProperties = RequestProperties.builder()
                .uri(requestURL)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<PopulationOfCountryData> baseIndividualUserResponseResponseProperties =
                restService.request(individualUserRegisterRequestProperties, PopulationOfCountryData.class);

        return baseIndividualUserResponseResponseProperties.getBody();
    }

    public CapitalOfCountryData capitalOfCountry(String country) {

        String requestURL = BASE_URL + "/capital/q?country=" + country;

        RequestProperties<Serializable> individualUserRegisterRequestProperties = RequestProperties.builder()
                .uri(requestURL)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<CapitalOfCountryData> baseIndividualUserResponseResponseProperties =
                restService.request(individualUserRegisterRequestProperties, CapitalOfCountryData.class);

        return baseIndividualUserResponseResponseProperties.getBody();
    }

    public LocationOfCountry locationOfCountry(String country) {

        String requestURL = BASE_URL + "/positions/q?country=" + country;

        RequestProperties<Serializable> individualUserRegisterRequestProperties = RequestProperties.builder()
                .uri(requestURL)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<LocationOfCountry> baseIndividualUserResponseResponseProperties =
                restService.request(individualUserRegisterRequestProperties, LocationOfCountry.class);

        return baseIndividualUserResponseResponseProperties.getBody();
    }

    public CurrencyOfCountry currencyOfCountry(String country) {

        String requestURL = BASE_URL + "/currency/q?country=" + country;

        RequestProperties<Serializable> individualUserRegisterRequestProperties = RequestProperties.builder()
                .uri(requestURL)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<CurrencyOfCountry> baseIndividualUserResponseResponseProperties =
                restService.request(individualUserRegisterRequestProperties, CurrencyOfCountry.class);

        return baseIndividualUserResponseResponseProperties.getBody();
    }

    public StatesOfCountry statesOfCountry(String country) {

        String requestURL = BASE_URL + "/states/q?country=" + country;

        RequestProperties<Serializable> individualUserRegisterRequestProperties = RequestProperties.builder()
                .uri(requestURL)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<StatesOfCountry> baseIndividualUserResponseResponseProperties =
                restService.request(individualUserRegisterRequestProperties, StatesOfCountry.class);

        return baseIndividualUserResponseResponseProperties.getBody();
    }

    public JsonNode statesOfCountry(String country, String state) {

        String requestURL = BASE_URL + "/state/cities/q?country=" + country + "&state=" + state;

        RequestProperties<Serializable> individualUserRegisterRequestProperties = RequestProperties.builder()
                .uri(requestURL)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<JsonNode> baseIndividualUserResponseResponseProperties =
                restService.request(individualUserRegisterRequestProperties, JsonNode.class);

        return baseIndividualUserResponseResponseProperties.getBody();
    }

}
