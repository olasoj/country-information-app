package com.example.countryinformationapplication.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "error",
        "msg",
        "data"
})
@Generated("jsonschema2pojo")
public class PopulationOfCityOfCountry implements Serializable {

    private static final long serialVersionUID = -358051477277316184L;
    @JsonProperty("error")
    private Boolean error;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private List<PopulationOfCityOfCountryData> data;

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
    public List<PopulationOfCityOfCountryData> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<PopulationOfCityOfCountryData> data) {
        this.data = data;
    }

}
