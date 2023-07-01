package com.example.countryinformationapplication.core.model.outbound;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "name",
        "iso3",
        "iso2",
        "states"
})
@Generated("jsonschema2pojo")
public class StatesOfCountry implements Serializable
{

    @JsonProperty("name")
    private String name;
    @JsonProperty("iso3")
    private String iso3;
    @JsonProperty("iso2")
    private String iso2;
    @JsonProperty("states")
    private List<State> states;
    private final static long serialVersionUID = -1003399840470022923L;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("iso3")
    public String getIso3() {
        return iso3;
    }

    @JsonProperty("iso3")
    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    @JsonProperty("iso2")
    public String getIso2() {
        return iso2;
    }

    @JsonProperty("iso2")
    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    @JsonProperty("states")
    public List<State> getStates() {
        return states;
    }

    @JsonProperty("states")
    public void setStates(List<State> states) {
        this.states = states;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof StatesOfCountry)) return false;

        StatesOfCountry that = (StatesOfCountry) obj;

        return new EqualsBuilder().append(name, that.name).append(iso3, that.iso3).append(iso2, that.iso2).append(states, that.states).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).append(iso3).append(iso2).append(states).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("iso3", iso3)
                .append("iso2", iso2)
                .append("states", states)
                .toString();
    }
}