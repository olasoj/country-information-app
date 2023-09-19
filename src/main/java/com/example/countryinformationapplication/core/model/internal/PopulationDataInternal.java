package com.example.countryinformationapplication.core.model.internal;

import com.example.countryinformationapplication.core.model.outbound.PopulationCount;
import com.example.countryinformationapplication.core.model.outbound.PopulationOfCityOfCountryData;
import com.example.countryinformationapplication.core.model.outbound.PopulationOfCountryData;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

public class PopulationDataInternal implements Serializable {

    private final int year;
    private final double value;
    private final String country;
    private final String city;

    public PopulationDataInternal(int year, double value, String country, String city) {
        this.year = year;
        this.value = value;
        this.country = country;
        this.city = city;
    }

    public static PopulationDataInternal assemble(PopulationOfCityOfCountryData populationOfCityOfCountryData) {
        Optional<PopulationCount> maxPopulationCount = PopulationCount.getMaxPopulationCount(populationOfCityOfCountryData.getPopulationCounts());
        return new PopulationDataInternal(
                maxPopulationCount.map(populationCount -> Integer.parseInt(populationCount.getYear())).orElse(0),
                maxPopulationCount.map(count -> Double.parseDouble(count.getValue())).orElse(0.0),
                populationOfCityOfCountryData.getCountry(),
                populationOfCityOfCountryData.getCity()
        );
    }

    public static PopulationDataInternal assembleByMaxYear(PopulationOfCountryData populationOfCityOfCountryData) {
        Optional<PopulationCount> maxPopulationCount = PopulationCount.getMaxPopulationCountByYear(populationOfCityOfCountryData.getPopulationCounts());
        return new PopulationDataInternal(
                maxPopulationCount.map(populationCount -> Integer.parseInt(populationCount.getYear())).orElse(0),
                maxPopulationCount.map(count -> Double.parseDouble(count.getValue())).orElse(0.0),
                populationOfCityOfCountryData.getCountry(),
                "N/A"
        );
    }

    public int getYear() {
        return year;
    }

    public double getValue() {
        return value;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PopulationDataInternal)) return false;
        PopulationDataInternal that = (PopulationDataInternal) obj;
        return year == that.year && Double.compare(that.value, value) == 0 && Objects.equals(country, that.country) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, value, country, city);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PopulationDataInternal.class.getSimpleName() + "[", "]")
                .add("year=" + year)
                .add("value=" + value)
                .add("country='" + country + "'")
                .add("city='" + city + "'")
                .toString();
    }
}
