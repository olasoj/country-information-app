package com.example.countryinformationapplication.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryInformationAppOutboundRestResponse {

    @JsonProperty("error")
    private Boolean error;
    @JsonProperty("msg")
    private String msg;

    public Boolean getError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof CountryInformationAppOutboundRestResponse otherCountryInformationAppOutboundRestResponse)) return false;

        return new EqualsBuilder()
                .append(error, otherCountryInformationAppOutboundRestResponse.error)
                .append(msg, otherCountryInformationAppOutboundRestResponse.msg)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(error)
                .append(msg)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("error", error)
                .append("msg", msg)
                .toString();
    }
}
