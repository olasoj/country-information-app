package com.example.countryinformationapplication.client.service.library.javahttp;

import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpException;
import com.example.countryinformationapplication.client.exception.CountryInformationApplicationHttpResourceTimeoutException;
import com.example.countryinformationapplication.client.model.internal.valueobject.RequestProperties;
import com.example.countryinformationapplication.client.model.internal.valueobject.ResponseProperties;
import com.example.countryinformationapplication.client.service.library.HttpLibrary;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class JavaHttpLibrary implements HttpLibrary {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaHttpLibrary.class);

    private final HttpClient javaHttpClient;
    private final ObjectMapper objectMapper;

    public JavaHttpLibrary(@Qualifier("javaHttpClient") HttpClient javaHttpClient, @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        this.javaHttpClient = javaHttpClient;
        this.objectMapper = objectMapper;
    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<String, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    @Override
    public <T extends Serializable, U> ResponseProperties<U> exchange(RequestProperties<T> requestProperties, Class<U> responseClassType) throws CountryInformationApplicationHttpException, CountryInformationApplicationHttpResourceTimeoutException {
        HttpMethod httpMethod = requestProperties.getHttpMethod();

        String[] httpHeaders = convertHeadersToArrayFormat(requestProperties.getHttpHeaders());

        T body = requestProperties.getBody();

        HttpRequest.BodyPublisher bodyPublisher = bodyPublisher(body);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(requestProperties.getUrl()))
                .headers(httpHeaders)
                .method(httpMethod.name(), bodyPublisher)
                .build();

        try {
            javaHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException interruptedException) {
            LOGGER.error(interruptedException.getMessage(), interruptedException);
            Thread.currentThread().interrupt();
        }

        return null;
    }

    private <T> HttpRequest.BodyPublisher bodyPublisher(T body) {
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.noBody();

        if (Objects.nonNull(body)) {
            Map<String, Object> requestBodyInMapFormat = objectMapper.convertValue(body, new TypeReference<>() {
            });

            bodyPublisher = buildFormDataFromMap(requestBodyInMapFormat);
        }
        return bodyPublisher;
    }

    private String[] convertHeadersToArrayFormat(HttpHeaders httpHeaders) {
        Map<String, String> headerInMapFormat = httpHeaders.toSingleValueMap();

        List<String> headers = new LinkedList<>();

        for (Map.Entry<String, String> entryValue : headerInMapFormat.entrySet()) {
            headers.add(entryValue.getKey());
            headers.add(entryValue.getValue());
        }

        return headers.toArray(String[]::new);
    }

}
