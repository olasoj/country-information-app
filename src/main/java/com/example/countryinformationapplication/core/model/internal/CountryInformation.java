package com.example.countryinformationapplication.core.model.internal;

import java.util.Currency;
import java.util.Objects;
import java.util.StringJoiner;

public class CountryInformation {

    private final PopulationDataInternal population;
    private final String capitalCity;
    private final LocationOfCountryInternal location;
    private final Currency currency;
    private final String iso2;
    private final String iso3;

    public CountryInformation(PopulationDataInternal population, String capitalCity, LocationOfCountryInternal location, Currency currency, String iso2, String iso3) {
        this.population = population;
        this.capitalCity = capitalCity;
        this.location = location;
        this.currency = currency;
        this.iso2 = iso2;
        this.iso3 = iso3;
    }

    public PopulationDataInternal getPopulation() {
        return population;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public LocationOfCountryInternal getLocation() {
        return location;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getIso2() {
        return iso2;
    }

    public String getIso3() {
        return iso3;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CountryInformation.class.getSimpleName() + "[", "]")
                .add("population=" + population)
                .add("capitalCity='" + capitalCity + "'")
                .add("location='" + location + "'")
                .add("currency=" + currency)
                .add("ISO2='" + iso2 + "'")
                .add("ISO3='" + iso3 + "'")
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CountryInformation)) return false;
        CountryInformation that = (CountryInformation) obj;
        return Objects.equals(population, that.population) && Objects.equals(capitalCity, that.capitalCity) && Objects.equals(location, that.location) && Objects.equals(currency, that.currency) && Objects.equals(iso2, that.iso2) && Objects.equals(iso3, that.iso3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(population, capitalCity, location, currency, iso2, iso3);
    }


}
