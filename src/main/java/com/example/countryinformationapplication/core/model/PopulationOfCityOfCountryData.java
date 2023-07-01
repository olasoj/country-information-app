package com.example.countryinformationapplication.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "city",
        "country",
        "populationCounts"
})
@Generated("jsonschema2pojo")
public class PopulationOfCityOfCountryData implements Serializable {

    private static final  long serialVersionUID = 2181877134545837031L;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("populationCounts")
    private List<PopulationCount> populationCounts;

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("populationCounts")
    public List<PopulationCount> getPopulationCounts() {
        return populationCounts;
    }

    @JsonProperty("populationCounts")
    public void setPopulationCounts(List<PopulationCount> populationCounts) {
        this.populationCounts = populationCounts;
    }

}