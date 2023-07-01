package com.example.countryinformationapplication.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "error",
        "msg",
        "data"
})
@Generated("jsonschema2pojo")
public class CapitalOfCountry implements Serializable {

    private static final long serialVersionUID = -7128424617832526502L;
    @JsonProperty("error")
    private Boolean error;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private CapitalOfCountryData data;

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
    public CapitalOfCountryData getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(CapitalOfCountryData data) {
        this.data = data;
    }

}
