package com.example.countryinformationapplication.client.model.internal.valueobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestProperties<T extends Serializable> implements Serializable {
    private final String url;
    private final HttpMethod httpMethod;
    private final HttpHeaders httpHeaders;
    private final T body;

    public RequestProperties(RequestPropertiesBuilder<T> requestPropertiesBuilder) {
        Assert.notNull(requestPropertiesBuilder, "RequestPropertiesBuilder cannot be null");

        this.url = requestPropertiesBuilder.url;
        this.httpMethod = requestPropertiesBuilder.httpMethod;
        this.httpHeaders = requestPropertiesBuilder.httpHeaders;
        this.body = requestPropertiesBuilder.body;
    }


    public static <T extends Serializable> RequestPropertiesBuilder<T> builder() {
        return new RequestPropertiesBuilder<>();
    }

    public static RequestProperties<Serializable> initGetRequestProperties(String uri) {
        return RequestProperties.builder()
                .uri(uri)
                .httpHeaders(new HttpHeaders())
                .httpMethod(HttpMethod.GET)
                .build();
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public T getBody() {
        return body;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.url)
                .append(this.httpHeaders)
                .append(this.httpMethod)
                .append(this.body)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof final RequestProperties<?> otherRequestProperties))
            return false;

        return new EqualsBuilder()
                .append(this.url, otherRequestProperties.url)
                .append(this.httpHeaders, otherRequestProperties.httpHeaders)
                .append(this.httpMethod, otherRequestProperties.httpMethod)
                .append(this.body, otherRequestProperties.body)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("url", this.url)
                .append("httpHeaders", this.httpHeaders)
                .append("httpMethod", this.httpMethod)
                .append("body", this.body)
                .toString();
    }

    public static class RequestPropertiesBuilder<B extends Serializable> {
        private String url;
        private HttpMethod httpMethod;
        private HttpHeaders httpHeaders;
        private B body;

        public RequestPropertiesBuilder<B> uri(String url) {
            this.url = url;
            return this;
        }

        public RequestPropertiesBuilder<B> httpHeaders(HttpHeaders httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public RequestPropertiesBuilder<B> httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public RequestPropertiesBuilder<B> body(B body) {
            this.body = body;
            return this;
        }

        public RequestProperties<B> build() {
            return new RequestProperties<>(this);
        }
    }
}
