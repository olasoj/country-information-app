package com.example.countryinformationapplication.core.model.outbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof PopulationOfCountryData)) return false;

        PopulationOfCountryData otherPopulationOfCountryData = (PopulationOfCountryData) obj;

        return new EqualsBuilder()
                .append(country, otherPopulationOfCountryData.country)
                .append(code, otherPopulationOfCountryData.code)
                .append(iso3, otherPopulationOfCountryData.iso3)
                .append(populationCounts, otherPopulationOfCountryData.populationCounts)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(country).append(code).append(iso3).append(populationCounts).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("country", country)
                .append("code", code)
                .append("iso3", iso3)
                .append("populationCounts", populationCounts)
                .toString();
    }
}
