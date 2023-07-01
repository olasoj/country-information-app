package com.example.countryinformationapplication.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;

import static java.net.http.HttpClient.Redirect.ALWAYS;
import static java.net.http.HttpClient.Version.HTTP_1_1;

@Configuration
public class JavaHttpClientConfig {

    @Bean
    public HttpClient javaHttpClient() {
        return HttpClient.newBuilder()
                .followRedirects(ALWAYS)
                .version(HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(15))
                .build();
    }
}
