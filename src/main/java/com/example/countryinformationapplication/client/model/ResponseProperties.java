package com.example.countryinformationapplication.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseProperties<T> {
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private T body;

    public ResponseProperties(ResponsePropertiesBuilder<T> responsePropertiesBuilder) {
        Assert.notNull(responsePropertiesBuilder, "ResponsePropertiesBuilder cannot be null");

        this.httpStatus = responsePropertiesBuilder.httpStatus;
        this.httpHeaders = responsePropertiesBuilder.httpHeaders;
        this.body = responsePropertiesBuilder.body;
    }

    public ResponseProperties() {
    }

    public static <T> ResponsePropertiesBuilder<T> builder() {
        return new ResponsePropertiesBuilder<>();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
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
                .append(this.httpStatus)
                .append(this.httpHeaders)
                .append(this.body)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof ResponseProperties<?>))
            return false;

        ResponseProperties<?> otherResponseProperties = (ResponseProperties<?>) obj;

        return new EqualsBuilder()
                .append(this.httpStatus, otherResponseProperties.httpStatus)
                .append(this.httpHeaders, otherResponseProperties.httpHeaders)
                .append(this.body, otherResponseProperties.body)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("httpStatus", this.httpStatus)
                .append("httpHeaders", this.httpHeaders)
                .append("body", this.body)
                .toString();
    }

    public static class ResponsePropertiesBuilder<B> {
        private HttpStatus httpStatus;
        private HttpHeaders httpHeaders;
        private B body;

        public ResponsePropertiesBuilder<B> httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public ResponsePropertiesBuilder<B> httpHeaders(HttpHeaders httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public ResponsePropertiesBuilder<B> body(B body) {
            this.body = body;
            return this;
        }

        public ResponseProperties<B> build() {
            return new ResponseProperties<>(this);
        }
    }
}
