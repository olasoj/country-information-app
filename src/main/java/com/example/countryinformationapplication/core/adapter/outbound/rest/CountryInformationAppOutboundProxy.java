package com.example.countryinformationapplication.core.adapter.outbound.rest;

import com.example.countryinformationapplication.core.model.outbound.*;

import java.util.List;

public interface CountryInformationAppOutboundProxy {
    List<PopulationOfCityOfCountryData> populationFilter(String country, int size);

    PopulationOfCountryData populationFilterByCountry(String country);

    CapitalOfCountryData capitalOfCountry(String country);

    LocationOfCountry locationOfCountry(String country);

    CurrencyOfCountry currencyOfCountry(String country);

    StatesOfCountry statesOfCountry(String country);

    List<String> citiesOfState(String country, String state);
}
