package com.example.countryinformationapplication.core.model.outbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "name",
        "capital",
        "iso2",
        "iso3"
})
@Generated("jsonschema2pojo")
public class CapitalOfCountryData implements Serializable {


    private static final long serialVersionUID = -5684078637218376531L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("capital")
    private String capital;
    @JsonProperty("iso2")
    private String iso2;
    @JsonProperty("iso3")
    private String iso3;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("capital")
    public String getCapital() {
        return capital;
    }

    @JsonProperty("capital")
    public void setCapital(String capital) {
        this.capital = capital;
    }

    @JsonProperty("iso2")
    public String getIso2() {
        return iso2;
    }

    @JsonProperty("iso2")
    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    @JsonProperty("iso3")
    public String getIso3() {
        return iso3;
    }

    @JsonProperty("iso3")
    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof CapitalOfCountryData)) return false;

        CapitalOfCountryData otherCapitalOfCountryData = (CapitalOfCountryData) obj;

        return new EqualsBuilder()
                .append(name, otherCapitalOfCountryData.name)
                .append(capital, otherCapitalOfCountryData.capital)
                .append(iso2, otherCapitalOfCountryData.iso2)
                .append(iso3, otherCapitalOfCountryData.iso3)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(capital)
                .append(iso2)
                .append(iso3)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("capital", capital)
                .append("iso2", iso2)
                .append("iso3", iso3)
                .toString();
    }
}
