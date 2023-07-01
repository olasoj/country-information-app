package com.example.countryinformationapplication.core.service;

import com.example.countryinformationapplication.core.adapter.outbound.rest.CountryInformationAppOutboundProxy;
import com.example.countryinformationapplication.core.model.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class CountryInformationAppService {

    private static final ExecutorService EXECUTOR_SERVICE_FOR_KYC_LIST_ITEM;
    private static final Scheduler RX_SCHEDULER_FOR_KYC_LIST_ITEM;

    static {
        int noOfCores = Math.min(Runtime.getRuntime().availableProcessors() * 3, 36);
        EXECUTOR_SERVICE_FOR_KYC_LIST_ITEM = new ThreadPoolExecutor(
                noOfCores,
                noOfCores + 10,
                50,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(40),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        RX_SCHEDULER_FOR_KYC_LIST_ITEM = Schedulers.from(EXECUTOR_SERVICE_FOR_KYC_LIST_ITEM);
    }

    private final CountryInformationAppOutboundProxy countryInformationAppOutboundProxy;

    protected CountryInformationAppService(@Qualifier("countryInformationAppOutboundProxy") CountryInformationAppOutboundProxy countryInformationAppOutboundProxy) {
        this.countryInformationAppOutboundProxy = countryInformationAppOutboundProxy;
    }

    //GET the top <number_of_cities> cities in order of population (descending) of Italy, New Zealand and Ghana,
    // where <number_of_cities> is a query parameter passed in the query. If there are not enough cities, just return the ones you can find;

    public Object topNumberOfCitiesBasedOnPopulation() {

        countryInformationAppOutboundProxy.populationFilter();
        return null;

    }

    public Object countryInformation(String country) {

        io.reactivex.rxjava3.functions.Supplier<PopulationOfCountryData> populationOfCountryDataSupplier = () -> countryInformationAppOutboundProxy.populationFilterByCountry(country);
        io.reactivex.rxjava3.functions.Supplier<CapitalOfCountryData> capitalOfCountryDataSupplier = () -> countryInformationAppOutboundProxy.capitalOfCountry(country);
        io.reactivex.rxjava3.functions.Supplier<LocationOfCountry> locationOfCountrySupplier = () -> countryInformationAppOutboundProxy.locationOfCountry(country);
        io.reactivex.rxjava3.functions.Supplier<CurrencyOfCountry> currencyOfCountryDataSupplier = () -> countryInformationAppOutboundProxy.currencyOfCountry(country);

        Observable<PopulationOfCountryData> populationOfCountryDataObservable = Observable.fromSupplier(populationOfCountryDataSupplier).subscribeOn(RX_SCHEDULER_FOR_KYC_LIST_ITEM);
        Observable<CapitalOfCountryData> capitalOfCountryDataObservable = Observable.fromSupplier(capitalOfCountryDataSupplier).subscribeOn(RX_SCHEDULER_FOR_KYC_LIST_ITEM);
        Observable<LocationOfCountry> locationOfCountryObservable = Observable.fromSupplier(locationOfCountrySupplier).subscribeOn(RX_SCHEDULER_FOR_KYC_LIST_ITEM);
        Observable<CurrencyOfCountry> currencyOfCountryObservable = Observable.fromSupplier(currencyOfCountryDataSupplier).subscribeOn(RX_SCHEDULER_FOR_KYC_LIST_ITEM);

        Observable<String> zip = Observable.zip(populationOfCountryDataObservable, capitalOfCountryDataObservable, locationOfCountryObservable, currencyOfCountryObservable,
                (po, ccDO, lOcO, ccOb) -> {
                    return "Optional.empty()";
                }
        );

        List<String> blockingSingle = zip
                .doOnNext(s -> System.out.println(Thread.currentThread().getName() + ": " + s))
                .toList()
                .blockingGet();

        return null;

    }

    public Object states(String country) {

        StatesOfCountry statesOfCountry = countryInformationAppOutboundProxy.statesOfCountry(country);
        List<State> states = statesOfCountry.getStates();

        Observable<State> stateObservable = Observable.fromIterable(states).subscribeOn(RX_SCHEDULER_FOR_KYC_LIST_ITEM);
        return null;
    }

}
