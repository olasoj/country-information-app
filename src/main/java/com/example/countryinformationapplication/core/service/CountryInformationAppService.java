package com.example.countryinformationapplication.core.service;

import java.math.BigDecimal;
import java.util.Map;

public interface CountryInformationAppService {
    Map<String, Object> topNumberOfCitiesBasedOnPopulation(int size);

    Map<String, Object> countryInformation(String country);

    Map<String, Object> states(String country);

    Map<String, Object> currencyExchanger(String country, String targetCurrency, BigDecimal amount);
}
