package com.example.nightingale.qwalk.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Kraft on 2017-05-12.
 */
public final class QLocation implements Parcelable {
    private final double latitude;
    private final double longitude;

    /**
     * Creates a new location at following longitude and latitude
     *
     * @param latitude
     * @param longitude
     */
    public QLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * @param location returns location
     */
    public QLocation(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    /**
     * Creates a new Location with a LatLng
     *
     * @param location
     */
    public QLocation(LatLng location) {
        this.latitude = location.latitude;
        this.longitude = location.longitude;
    }

    /**
     * @return returns latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return returns longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return converts to android Location
     */
    public Location toAndroidLocation() {
        Location l = new Location("");
        l.setLatitude(latitude);
        l.setLongitude(longitude);
        return l;
    }

    /**
     * @return converts to android LatLng
     */
    public LatLng toLatLng() {
        return new LatLng(latitude, longitude);
    }

    /**
     * Calculates the distance in meters between this location and another
     *
     * @param other the location you want to measure the distance to
     * @return returns the distance in meters
     */
    public double distanceTo(QLocation other) {
        return measure(getLatitude(), getLongitude(), other.getLatitude(), other.getLongitude()); //Math.sqrt(Math.pow(this.getLatitude() - other.latitude, 2) + Math.pow(this.getLongitude() - other.getLongitude(), 2));
    }

    private static double measure(double latitude1, double longitude1, double latitude2, double longitude2) {  // generally used geo measurement function
        double r = 6378.137; // Radius of earth in KM
        double dLat = latitude2 * Math.PI / 180 - latitude1 * Math.PI / 180;
        double dLon = longitude2 * Math.PI / 180 - longitude1 * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(latitude1 * Math.PI / 180) *
                Math.cos(latitude2 * Math.PI / 180) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d * 1000; // meters
    }

    /**
     * Calculates the distance in just one axis, longitude.
     *
     * @param location the location you want to measure the distance to
     * @return returns the distance in longitude units
     */
    public double deltaLongitude(QLocation location) {
        return location.getLongitude() - this.getLongitude();
    }


    /**
     * Calculates the distance in just one axis, latitude.
     *
     * @param location the location you want to measure the distance to
     * @return returns the distance in latitude units
     */
    public double deltaLatitude(QLocation location) {
        return location.getLatitude() - this.getLatitude();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof QLocation) {
            QLocation a = (QLocation) o;
            return a.getLongitude() == getLongitude() && a.getLatitude() == getLatitude();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        //TODO
        return 0;
    }

    protected QLocation(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<QLocation> CREATOR = new Parcelable.Creator<QLocation>() {
        @Override
        public QLocation createFromParcel(Parcel in) {
            return new QLocation(in);
        }

        @Override
        public QLocation[] newArray(int size) {
            return new QLocation[size];
        }
    };
}
