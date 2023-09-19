package com.example.countryinformationapplication.core.adapter.outbound.rest;

import com.example.countryinformationapplication.client.model.RequestProperties;
import com.example.countryinformationapplication.client.model.ResponseProperties;
import com.example.countryinformationapplication.client.service.rest.RestService;
import com.example.countryinformationapplication.core.model.outbound.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

@Service
public class DefaultCountryInformationAppOutboundProxy implements CountryInformationAppOutboundProxy {
    private static final String BASE_URL = "https://countriesnow.space/api/v0.1/countries";
    private static final String COUNTRY_QUERY_KEY = "country";
    private final RestService restService;
    private final ObjectMapper objectMapper;

    public DefaultCountryInformationAppOutboundProxy(@Qualifier("countryInformationAppOutboundRestService") RestService restService, @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.restService = restService;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<PopulationOfCityOfCountryData> populationFilter(String country, int size) {

        validateArgs(country);

        URI uri = UriComponentsBuilder.fromUriString(BASE_URL + "/population/cities/filter/q")
                .queryParam(COUNTRY_QUERY_KEY, country)
                .queryParam("orderBy", "population")
                .queryParam("order", SortDirection.DESC.getName())
                .queryParam("limit", size)
                .build().toUri();


        RequestProperties<Serializable> requestProperties = RequestProperties.builder()
                .uri(uri)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<ArrayNode> responseProperties =
                restService.request(requestProperties, ArrayNode.class);

        ArrayNode body = responseProperties.getBody();

        return objectMapper.convertValue(body, new TypeReference<>() {
        });
    }

    @Override
    public PopulationOfCountryData populationFilterByCountry(String country) {

        validateArgs(country);

        URI uri = UriComponentsBuilder.fromUriString(BASE_URL + "/population/q")
                .queryParam(COUNTRY_QUERY_KEY, country)
                .build().toUri();

        RequestProperties<Serializable> requestProperties = RequestProperties.builder()
                .uri(uri)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<PopulationOfCountryData> responseProperties =
                restService.request(requestProperties, PopulationOfCountryData.class);

        return responseProperties.getBody();
    }

    @Override
    public CapitalOfCountryData capitalOfCountry(String country) {

        validateArgs(country);

        URI uri = UriComponentsBuilder.fromUriString(BASE_URL + "/capital/q")
                .queryParam(COUNTRY_QUERY_KEY, country)
                .build().toUri();

        RequestProperties<Serializable> requestProperties = RequestProperties.builder()
                .uri(uri)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<CapitalOfCountryData> responseProperties =
                restService.request(requestProperties, CapitalOfCountryData.class);

        return responseProperties.getBody();
    }

    private void validateArgs(String country) {
        validateArgs(country, "Country");
        if (StringUtils.isBlank(country)) throw new IllegalArgumentException("Country cannot be blank");
    }

    private void validateArgs(String country, String varName) {
        if (StringUtils.isBlank(country)) throw new IllegalArgumentException(varName + " cannot be blank");
    }

    @Override
    public LocationOfCountry locationOfCountry(String country) {

        validateArgs(country);

        URI uri = UriComponentsBuilder.fromUriString(BASE_URL + "/positions/q")
                .queryParam(COUNTRY_QUERY_KEY, country)
                .build().toUri();

        RequestProperties<Serializable> requestProperties = RequestProperties.builder()
                .uri(uri)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<LocationOfCountry> responseProperties =
                restService.request(requestProperties, LocationOfCountry.class);

        return responseProperties.getBody();
    }

    @Override
    public CurrencyOfCountry currencyOfCountry(String country) {

        validateArgs(country);

        URI uri = UriComponentsBuilder.fromUriString(BASE_URL + "/currency/q")
                .queryParam(COUNTRY_QUERY_KEY, country)
                .build().toUri();

        RequestProperties<Serializable> requestProperties = RequestProperties.builder()
                .uri(uri)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<CurrencyOfCountry> responseProperties =
                restService.request(requestProperties, CurrencyOfCountry.class);

        return responseProperties.getBody();
    }

    @Override
    public StatesOfCountry statesOfCountry(String country) {

        validateArgs(country);

        URI uri = UriComponentsBuilder.fromUriString(BASE_URL + "/states/q")
                .queryParam(COUNTRY_QUERY_KEY, country)
                .build().toUri();

        RequestProperties<Serializable> requestProperties = RequestProperties.builder()
                .uri(uri)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<StatesOfCountry> responseProperties =
                restService.request(requestProperties, StatesOfCountry.class);

        return responseProperties.getBody();
    }

    @Override
    public List<String> citiesOfState(String country, String state) {

        validateArgs(country);
        validateArgs(state, "State");


        URI uri = UriComponentsBuilder.fromUriString(BASE_URL + "/state/cities/q")
                .queryParam(COUNTRY_QUERY_KEY, country)
                .queryParam("state", state)
                .build().toUri();

        RequestProperties<Serializable> requestProperties = RequestProperties.builder()
                .uri(uri)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();

        ResponseProperties<ArrayNode> responseProperties =
                restService.request(requestProperties, ArrayNode.class);

        return objectMapper.convertValue(responseProperties.getBody(), new TypeReference<>() {
        });
    }

    enum SortDirection {
        ASC("asc"),
        DESC("dsc");

        private final String name;

        SortDirection(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
