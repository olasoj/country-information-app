package com.example.countryinformationapplication.core.model.outbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "city",
        "country",
        "populationCounts"
})
@Generated("jsonschema2pojo")
public class PopulationOfCityOfCountryData implements Serializable {

    private static final long serialVersionUID = 2181877134545837031L;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("populationCounts")
    private List<PopulationCount> populationCounts;

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
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

        if (!(obj instanceof PopulationOfCityOfCountryData)) return false;

        PopulationOfCityOfCountryData otherPopulationOfCityOfCountryData = (PopulationOfCityOfCountryData) obj;

        return new EqualsBuilder()
                .append(city, otherPopulationOfCityOfCountryData.city)
                .append(country, otherPopulationOfCityOfCountryData.country)
                .append(populationCounts, otherPopulationOfCityOfCountryData.populationCounts)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(city)
                .append(country)
                .append(populationCounts)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("city", city)
                .append("country", country)
                .append("populationCounts", populationCounts)
                .toString();
    }

    public static Comparator<PopulationOfCityOfCountryData> populationOfCityOfCountryDataComparator() {
        return (o1, o2) -> {
            List<PopulationCount> o1PopulationCounts = o1.getPopulationCounts();
            List<PopulationCount> o2PopulationCounts = o2.getPopulationCounts();

            Optional<PopulationCount> maxPopulationCount = PopulationCount.getMaxPopulationCount(o1PopulationCounts);
            Optional<PopulationCount> maxPopulationCount2 = PopulationCount.getMaxPopulationCount(o2PopulationCounts);

            if (maxPopulationCount.isEmpty() && maxPopulationCount2.isEmpty()) return 0;
            if (maxPopulationCount.isPresent() && maxPopulationCount2.isEmpty()) return 1;
            if (maxPopulationCount.isEmpty()) return -1;

            PopulationCount populationCount = maxPopulationCount.get();
            PopulationCount populationCount2 = maxPopulationCount2.get();

            return (populationCount.compareTo(populationCount2)) == 0 ?
                    0 :
                    (populationCount.compareTo(populationCount2)) * -1;

        };
    }
}