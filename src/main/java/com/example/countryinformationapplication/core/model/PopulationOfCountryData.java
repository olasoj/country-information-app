package com.example.countryinformationapplication.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "country",
        "code",
        "iso3",
        "populationCounts"
})
@Generated("jsonschema2pojo")
public class PopulationOfCountryData implements Serializable {

    private final static long serialVersionUID = -5074960871427931682L;
    @JsonProperty("country")
    private String country;
    @JsonProperty("code")
    private String code;
    @JsonProperty("iso3")
    private String iso3;
    @JsonProperty("populationCounts")
    private List<PopulationCount> populationCounts;

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("iso3")
    public String getIso3() {
        return iso3;
    }

    @JsonProperty("iso3")
    public void setIso3(String iso3) {
        this.iso3 = iso3;
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
