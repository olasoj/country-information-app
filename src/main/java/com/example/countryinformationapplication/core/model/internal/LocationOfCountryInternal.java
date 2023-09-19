package com.example.countryinformationapplication.core.model.internal;

import java.util.Objects;
import java.util.StringJoiner;

public class LocationOfCountryInternal {

    private final int longitude;
    private final int latitude;

    public LocationOfCountryInternal(int longitude, int latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LocationOfCountryInternal.class.getSimpleName() + "[", "]")
                .add("longitude='" + longitude + "'")
                .add("latitude='" + latitude + "'")
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LocationOfCountryInternal)) return false;
        LocationOfCountryInternal that = (LocationOfCountryInternal) obj;
        return Objects.equals(longitude, that.longitude) && Objects.equals(latitude, that.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }
}
