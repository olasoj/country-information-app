package com.example.countryinformationapplication.core.adapter.inbound.rest;

import com.example.countryinformationapplication.core.service.DefaultCountryInformationAppService;
import com.example.countryinformationapplication.util.response.model.Response;
import com.example.countryinformationapplication.util.response.transformer.ResponseAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@Validated
@RequestMapping(path = "/country")
public class CountryInformationAppController {
    private final DefaultCountryInformationAppService countryInformationAppService;

    public CountryInformationAppController(DefaultCountryInformationAppService countryInformationAppService) {
        this.countryInformationAppService = countryInformationAppService;
    }

    @GetMapping(path = "/population/city/filter")
    public ResponseEntity<Response<Map<String, Object>>> filter(@Valid @RequestParam("querySize") @NotNull(message = "Enter query size") @Min(1) Integer querySize) {
        var topNumberOfCitiesBasedOnPopulation = countryInformationAppService.topNumberOfCitiesBasedOnPopulation(querySize);
        var individualUserCreationResponseDataResponse = ResponseAssembler.toResponse(HttpStatus.OK, topNumberOfCitiesBasedOnPopulation);
        return ResponseEntity.ok().body(individualUserCreationResponseDataResponse);
    }

    @GetMapping(path = "/information")
    public ResponseEntity<Response<Map<String, Object>>> information(@RequestParam("country") @NotBlank(message = "Enter country") String country) {
        var individualUser = countryInformationAppService.countryInformation(country);
        var individualUserCreationResponseDataResponse = ResponseAssembler.toResponse(HttpStatus.OK, individualUser);
        return ResponseEntity.ok().body(individualUserCreationResponseDataResponse);
    }

    @GetMapping(path = "/state-with-cities")
    public ResponseEntity<Response<Map<String, Object>>> states(@Valid @NotBlank(message = "Enter country") @RequestParam("country") String country) {
        var updateUser = countryInformationAppService.states(country);
        var individualUserCreationResponseDataResponse = ResponseAssembler.toResponse(HttpStatus.OK, updateUser);
        return ResponseEntity.ok().body(individualUserCreationResponseDataResponse);
    }

    @GetMapping(path = "/exchange-rate")
    public ResponseEntity<Response<Map<String, Object>>> exchange(
            @Valid @NotBlank(message = "Enter country") @RequestParam("country") String country,
            @Valid @NotBlank(message = "Enter targetCurrency") @RequestParam("targetCurrency") String targetCurrency,
            @Valid @NotNull(message = "Enter amount") @Min(0) @RequestParam("amount") BigDecimal amount) {
        var updateUser = countryInformationAppService.currencyExchanger(country, targetCurrency, amount);
        var individualUserCreationResponseDataResponse = ResponseAssembler.toResponse(HttpStatus.OK, updateUser);
        return ResponseEntity.ok().body(individualUserCreationResponseDataResponse);
    }

}
