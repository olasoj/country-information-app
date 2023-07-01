package com.example.countryinformationapplication.core.service;

import com.example.countryinformationapplication.core.model.internal.ExchangeRate;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExchangeRateStore {
    private static final List<ExchangeRate> EXCHANGE_RATES;
    private static final Map<String, ExchangeRate> EXCHANGE_RATES_MAP;

    static {
        EXCHANGE_RATES = List.of(

                new ExchangeRate("EUR", "NGN", BigDecimal.valueOf(493.06)),
                new ExchangeRate("USD", "NGN", BigDecimal.valueOf(460.72)),
                new ExchangeRate("JPY", "NGN", BigDecimal.valueOf(3.28)),
                new ExchangeRate("GBP", "NGN", BigDecimal.valueOf(570.81)),

                new ExchangeRate("EUR", "UGX", BigDecimal.valueOf(4004.33)),
                new ExchangeRate("USD", "UGX", BigDecimal.valueOf(3739.83)),
                new ExchangeRate("JPY", "UGX", BigDecimal.valueOf(26.62)),
                new ExchangeRate("GBP", "UGX", BigDecimal.valueOf(4633.48))
        );

        EXCHANGE_RATES_MAP = EXCHANGE_RATES
                .stream()
                .collect(Collectors.toUnmodifiableMap(exchangeRate -> exchangeRate.sourceCurrency() + exchangeRate.targetCurrency(), Function.identity()));
    }

    private ExchangeRateStore() {
    }

    public static Optional<ExchangeRate> lookUpExchangeRateBySourceAndTargetCurrency(String sourceCurrency, String targetCurrency) {
        Assert.notNull(sourceCurrency, "SourceCurrency cannot be null");
        Assert.notNull(targetCurrency, "TargetCurrency cannot be null");

        return Optional.ofNullable(EXCHANGE_RATES_MAP.get(sourceCurrency.trim() + targetCurrency.trim()));
    }
}
