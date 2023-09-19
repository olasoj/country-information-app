package com.example.countryinformationapplication.core.model.outbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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

import static java.util.Objects.isNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "year",
        "value",
        "sex",
        "reliabilty"
})
@Generated("jsonschema2pojo")
public class PopulationCount implements Serializable, Comparable<PopulationCount> {

    public static final Comparator<PopulationCount> POPULATION_COUNT_NATURAL_ORDER = Comparator.naturalOrder();

    public static final Comparator<PopulationCount> POPULATION_COUNT_BY_YEAR = (o1, o2) -> {
        return Integer.parseInt(o1.getYear()) - Integer.parseInt(o2.getYear());
    };

    private final static long serialVersionUID = 4749335940891934440L;
    @JsonProperty("year")
    private String year;
    @JsonProperty("value")
    private String value;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("reliabilty")
    private String reliabilty;

    public static Optional<PopulationCount> getMaxPopulationCount(List<PopulationCount> populationCounts) {
        return isNull(populationCounts) ? Optional.empty() : populationCounts.stream()
                .sorted(POPULATION_COUNT_NATURAL_ORDER.reversed())
                .limit(1)
                .findFirst();
    }

    public static Optional<PopulationCount> getMaxPopulationCountByYear(List<PopulationCount> populationCounts) {
        return isNull(populationCounts) ? Optional.empty() : populationCounts.stream()
                .sorted(POPULATION_COUNT_BY_YEAR.reversed())
                .limit(1)
                .findFirst();
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof PopulationCount)) return false;

        PopulationCount otherPopulationCount = (PopulationCount) obj;

        return new EqualsBuilder()
                .append(year, otherPopulationCount.year)
                .append(value, otherPopulationCount.value)
                .append(sex, otherPopulationCount.sex)
                .append(reliabilty, otherPopulationCount.reliabilty)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(year).append(value).append(sex).append(reliabilty).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("year", year)
                .append("value", value)
                .append("sex", sex)
                .append("reliabilty", reliabilty)
                .toString();
    }

    @Override
    public int compareTo(PopulationCount otherPopulationCount) {
        double populationCount1Value = Double.parseDouble(this.getValue());
        double populationCount2Value = Double.parseDouble(otherPopulationCount.getValue());

        if (populationCount1Value > populationCount2Value)
            return 1;
        else if (populationCount1Value < populationCount2Value)
            return -1;
        else return Integer.parseInt(this.getYear()) > Integer.parseInt(otherPopulationCount.getYear()) ? 1 : -1;
    }
}
