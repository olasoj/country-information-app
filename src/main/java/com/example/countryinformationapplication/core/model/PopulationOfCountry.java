package com.example.countryinformationapplication.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "error",
        "msg",
        "data"
})
@Generated("jsonschema2pojo")
public class PopulationOfCountry implements Serializable {

    private final static long serialVersionUID = -6806671962403941471L;
    @JsonProperty("error")
    private Boolean error;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private PopulationOfCountryData populationOfCountryData;

    @JsonProperty("error")
    public Boolean getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(Boolean error) {
        this.error = error;
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    @JsonProperty("msg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty("data")
    public PopulationOfCountryData getData() {
        return populationOfCountryData;
    }

    @JsonProperty("data")
    public void setData(PopulationOfCountryData populationOfCountryData) {
        this.populationOfCountryData = populationOfCountryData;
    }

}