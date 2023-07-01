package com.example.countryinformationapplication.core.adapter.inbound.rest;

import com.example.countryinformationapplication.core.service.DefaultCountryInformationAppService;
import com.example.countryinformationapplication.util.response.model.Response;
import com.example.countryinformationapplication.util.response.transformer.ResponseAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CountryInformationAppControllerTest {

    @Mock()
    private DefaultCountryInformationAppService countryInformationAppService;

    @InjectMocks
    private CountryInformationAppController individualUserRestController;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Response<Map<String, Object>>> jacksonTester;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(individualUserRestController)
//                .setControllerAdvice(new SuperHeroExceptionHandler())
                .build();
    }

    @Test
    void filter() throws Exception {

        //given
        Map<String, Object> responseObject = new HashMap<>();
        var expectedResponse = ResponseAssembler.toResponse(HttpStatus.OK, responseObject);

        given(countryInformationAppService.topNumberOfCitiesBasedOnPopulation(3))
                .willReturn(responseObject);

        //when
        MockHttpServletResponse response = mvc.perform(
                        get("/country/population/city/filter?querySize=3")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        then(response.getContentAsString()).isEqualTo(jsonIndividualUserRegisterResponse.write(expectedResponse).getJson());
    }

    @Test
    void information() throws Exception {

        //given
        Map<String, Object> responseObject = new HashMap<>();

        given(countryInformationAppService.countryInformation("Nigeria"))
                .willReturn(responseObject);

        //when
        MockHttpServletResponse response = mvc.perform(
                        get("/country/information/?country=Nigeria")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void states() throws Exception {

        //given
        Map<String, Object> responseObject = new HashMap<>();

        given(countryInformationAppService.states("Nigeria"))
                .willReturn(responseObject);

        //when
        MockHttpServletResponse response = mvc.perform(
                        get("/country/state-with-cities/?country=Nigeria")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void exchange() throws Exception {

        //given
        Map<String, Object> responseObject = new HashMap<>();

        given(countryInformationAppService.currencyExchanger("Nigeria", "NGN", BigDecimal.valueOf(20)))
                .willReturn(responseObject);

        //when
        MockHttpServletResponse response = mvc.perform(
                        get("/country/exchange-rate/?country=Japan&targetCurrency=NGN&amount=998.908789")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}