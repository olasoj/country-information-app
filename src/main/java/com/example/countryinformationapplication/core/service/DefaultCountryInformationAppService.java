package com.example.countryinformationapplication.core.service;

import com.example.countryinformationapplication.core.adapter.outbound.rest.CountryInformationAppOutboundProxy;
import com.example.countryinformationapplication.core.model.internal.CountryInformation;
import com.example.countryinformationapplication.core.model.internal.ExchangeRate;
import com.example.countryinformationapplication.core.model.internal.LocationOfCountryInternal;
import com.example.countryinformationapplication.core.model.internal.PopulationDataInternal;
import com.example.countryinformationapplication.core.model.outbound.*;
import com.example.countryinformationapplication.util.JacksonUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DefaultCountryInformationAppService implements CountryInformationAppService {

    private static final ExecutorService EXECUTOR_SERVICE;
    private static final Scheduler RX_SCHEDULER;
    private static final int TIMEOUT = 20;

    static {
        int noOfCores = Runtime.getRuntime().availableProcessors();
        EXECUTOR_SERVICE = new ThreadPoolExecutor(
                noOfCores,
                1 + noOfCores,
                50,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(400),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        RX_SCHEDULER = Schedulers.from(EXECUTOR_SERVICE);
    }

    private final CountryInformationAppOutboundProxy countryInformationAppOutboundProxy;

    protected DefaultCountryInformationAppService(@Qualifier("defaultCountryInformationAppOutboundProxy") CountryInformationAppOutboundProxy countryInformationAppOutboundProxy) {
        this.countryInformationAppOutboundProxy = countryInformationAppOutboundProxy;
    }

    @PreDestroy
    public void destroy() {
        EXECUTOR_SERVICE.shutdown();
        if (!EXECUTOR_SERVICE.isShutdown())
            EXECUTOR_SERVICE.shutdownNow();
    }

    @Override
    public Map<String, Object> topNumberOfCitiesBasedOnPopulation(int size) {

        io.reactivex.rxjava3.functions.Supplier<List<PopulationOfCityOfCountryData>> populationOfCityOfNigeriaDataSupplier = () -> countryInformationAppOutboundProxy.populationFilter("Ghana", size);
        io.reactivex.rxjava3.functions.Supplier<List<PopulationOfCityOfCountryData>> populationOfCityOfItalyDataSupplier = () -> countryInformationAppOutboundProxy.populationFilter("Italy", size);
        io.reactivex.rxjava3.functions.Supplier<List<PopulationOfCityOfCountryData>> populationOfCityOfNewZealandDataSupplier = () -> countryInformationAppOutboundProxy.populationFilter("New Zealand", size);

        Observable<List<PopulationOfCityOfCountryData>> populationOfCityOfNigeriaDataObservable = Observable.fromSupplier(populationOfCityOfNigeriaDataSupplier).subscribeOn(RX_SCHEDULER);
        Observable<List<PopulationOfCityOfCountryData>> populationOfCityOfItalyDataObservable = Observable.fromSupplier(populationOfCityOfItalyDataSupplier).subscribeOn(RX_SCHEDULER);
        Observable<List<PopulationOfCityOfCountryData>> populationOfCityOfNewZealandDataObservable = Observable.fromSupplier(populationOfCityOfNewZealandDataSupplier).subscribeOn(RX_SCHEDULER);

        return Observable.zip(populationOfCityOfNigeriaDataObservable, populationOfCityOfItalyDataObservable, populationOfCityOfNewZealandDataObservable,
                        (populationOfCityOfNigeriaData, populationOfCityOfItalyData, populationOfCityOfNewZealandData) -> {

                            //List  of cities
                            List<PopulationDataInternal> populationOfCityOfCountryDataAggregate =
                                    Stream.of(populationOfCityOfNigeriaData, populationOfCityOfItalyData, populationOfCityOfNewZealandData)
                                            .flatMap(Collection::stream)
                                            .sorted(PopulationOfCityOfCountryData.populationOfCityOfCountryDataComparator())
                                            .limit(size)
                                            .map(PopulationDataInternal::assemble)
                                            .collect(Collectors.toList());

                            Map<String, Object> map = new HashMap<>();
                            map.put("Result", populationOfCityOfCountryDataAggregate);
                            return map;
                        }
                )
                .doOnNext(JacksonUtil::logReqRes)
                .timeout(TIMEOUT, TimeUnit.SECONDS)
                .blockingSingle();
    }

    @Override
    public Map<String, Object> countryInformation(String country) {

        io.reactivex.rxjava3.functions.Supplier<PopulationOfCountryData> populationOfCountryDataSupplier = () -> countryInformationAppOutboundProxy.populationFilterByCountry(country);
        io.reactivex.rxjava3.functions.Supplier<CapitalOfCountryData> capitalOfCountryDataSupplier = () -> countryInformationAppOutboundProxy.capitalOfCountry(country);
        io.reactivex.rxjava3.functions.Supplier<LocationOfCountry> locationOfCountrySupplier = () -> countryInformationAppOutboundProxy.locationOfCountry(country);
        io.reactivex.rxjava3.functions.Supplier<CurrencyOfCountry> currencyOfCountryDataSupplier = () -> countryInformationAppOutboundProxy.currencyOfCountry(country);

        Observable<PopulationOfCountryData> populationOfCountryDataObservable = Observable.fromSupplier(populationOfCountryDataSupplier).subscribeOn(RX_SCHEDULER);
        Observable<CapitalOfCountryData> capitalOfCountryDataObservable = Observable.fromSupplier(capitalOfCountryDataSupplier).subscribeOn(RX_SCHEDULER);
        Observable<LocationOfCountry> locationOfCountryObservable = Observable.fromSupplier(locationOfCountrySupplier).subscribeOn(RX_SCHEDULER);
        Observable<CurrencyOfCountry> currencyOfCountryObservable = Observable.fromSupplier(currencyOfCountryDataSupplier).subscribeOn(RX_SCHEDULER);

        return Observable.zip(populationOfCountryDataObservable, capitalOfCountryDataObservable, locationOfCountryObservable, currencyOfCountryObservable,
                        (populationOfCountryData, capitalOfCountryData, locationOfCountry, currencyOfCountry) -> {

                            CountryInformation countryInformation = new CountryInformation(
                                    PopulationDataInternal.assembleByMaxYear(populationOfCountryData)
                                    , capitalOfCountryData.getCapital()
                                    , new LocationOfCountryInternal(locationOfCountry.getLong(), locationOfCountry.getLat())
                                    , Currency.getInstance(currencyOfCountry.getCurrency())
                                    , currencyOfCountry.getIso2()
                                    , currencyOfCountry.getIso3()
                            );

                            Map<String, Object> map = new HashMap<>();
                            map.put("Result", countryInformation);
                            return map;
                        }
                )
                .doOnNext(JacksonUtil::logReqRes)
                .timeout(TIMEOUT, TimeUnit.SECONDS)
                .blockingSingle();
    }

    @Override
    public Map<String, Object> states(String country) {

        StatesOfCountry statesOfCountry = countryInformationAppOutboundProxy.statesOfCountry(country);
        List<State> states = statesOfCountry.getStates();

        Map<String, Object> map = new HashMap<>();

        Observable
                .fromIterable(states)
                .flatMap(e ->

                        Observable.just(e)
                                .subscribeOn(RX_SCHEDULER)
                                .map(ev -> {
                                    try {
                                        String stateName = ev.getName();
                                        map.put(stateName, countryInformationAppOutboundProxy.citiesOfState(country, stateName.trim()));
                                        return stateName;
                                    } catch (Exception ignored) {
                                    }
                                    return "";
                                })

                )
                .doOnNext(JacksonUtil::logReqRes)
                .timeout(TIMEOUT, TimeUnit.SECONDS)
                .blockingSubscribe();

        JacksonUtil.logReqRes(map);

        return map;
    }

    @Override
    public Map<String, Object> currencyExchanger(String country, String targetCurrency, BigDecimal amount) {

        CurrencyOfCountry currencyOfCountry = countryInformationAppOutboundProxy.currencyOfCountry(country);

        String sourceCurrency = currencyOfCountry.getCurrency();
        Optional<ExchangeRate> exchangeRateOptional = ExchangeRateStore.lookUpExchangeRateBySourceAndTargetCurrency(sourceCurrency, targetCurrency);

        BigDecimal exchangedAmount = exchangeRateOptional
                .map(exchangeRate -> exchangeRate.rate().multiply(amount).setScale(2, RoundingMode.FLOOR))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country currency not supported"));

        JacksonUtil.logReqRes(currencyOfCountry);
        JacksonUtil.logReqRes(exchangedAmount);

        Map<String, Object> map = new HashMap<>();
        map.put("sourceCurrency", sourceCurrency);
        map.put("exchangedAmount", exchangedAmount);

        return map;
    }


}
