package com.example.countryinformationapplication.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "year",
        "value",
        "sex",
        "reliabilty"
})
@Generated("jsonschema2pojo")
public class PopulationCount implements Serializable {

    private final static long serialVersionUID = 4749335940891934440L;
    @JsonProperty("year")
    private String year;
    @JsonProperty("value")
    private String value;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("reliabilty")
    private String reliabilty;

    @JsonProperty("year")
    public String getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(String year) {
        this.year = year;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("sex")
    public String getSex() {
        return sex;
    }

    @JsonProperty("sex")
    public void setSex(String sex) {
        this.sex = sex;
    }

    @JsonProperty("reliabilty")
    public String getReliabilty() {
        return reliabilty;
    }

    @JsonProperty("reliabilty")
    public void setReliabilty(String reliabilty) {
        this.reliabilty = reliabilty;
    }

}
