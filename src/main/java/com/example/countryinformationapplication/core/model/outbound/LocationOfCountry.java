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
        "iso2",
        "long",
        "lat"
})
@Generated("jsonschema2pojo")
public class LocationOfCountry implements Serializable {

    private final static long serialVersionUID = 7120112123410682167L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("iso2")
    private String iso2;
    @JsonProperty("long")
    private Integer _long;
    @JsonProperty("lat")
    private Integer lat;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("iso2")
    public String getIso2() {
        return iso2;
    }

    @JsonProperty("iso2")
    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    @JsonProperty("long")
    public Integer getLong() {
        return _long;
    }

    @JsonProperty("long")
    public void setLong(Integer _long) {
        this._long = _long;
    }

    @JsonProperty("lat")
    public Integer getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Integer lat) {
        this.lat = lat;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof LocationOfCountry)) return false;

        LocationOfCountry otherLocationOfCountry = (LocationOfCountry) obj;

        return new EqualsBuilder()
                .append(name, otherLocationOfCountry.name)
                .append(iso2, otherLocationOfCountry.iso2)
                .append(_long, otherLocationOfCountry._long)
                .append(lat, otherLocationOfCountry.lat)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(iso2)
                .append(_long)
                .append(lat)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("iso2", iso2)
                .append("_long", _long)
                .append("lat", lat)
                .toString();
    }
}