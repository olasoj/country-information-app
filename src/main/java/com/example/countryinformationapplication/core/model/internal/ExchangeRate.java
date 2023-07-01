package com.example.countryinformationapplication.core.model.internal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public final class ExchangeRate {
    private final String sourceCurrency;
    private final String targetCurrency;
    private final BigDecimal rate;

    public ExchangeRate(String sourceCurrency, String targetCurrency, BigDecimal rate) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public String sourceCurrency() {
        return sourceCurrency;
    }

    public String targetCurrency() {
        return targetCurrency;
    }

    public BigDecimal rate() {
        return rate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof ExchangeRate)) return false;

        ExchangeRate otherExchangeRate = (ExchangeRate) obj;

        return new EqualsBuilder()
                .append(sourceCurrency, otherExchangeRate.sourceCurrency)
                .append(targetCurrency, otherExchangeRate.targetCurrency)
                .append(rate, otherExchangeRate.rate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sourceCurrency)
                .append(targetCurrency)
                .append(rate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sourceCurrency", sourceCurrency)
                .append("targetCurrency", targetCurrency)
                .append("rate", rate)
                .toString();
    }
}
