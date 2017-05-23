package com.example.nightingale.qwalk.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Kraft on 2017-05-12.
 */ // TODO detta kanske kan göras till en adapter eller något idk, har massa dependencies nu
public final class QLocation implements Parcelable{
    private final double latitude;
    private final double longitude;


    /**
     * Creates a new location at following longitude and latitude
     * @param latitude
     * @param longitude
     */
    public QLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     *
     * @param location
     */
    public QLocation(Location location){
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    /**
     *
     * @param location
     */
    public QLocation(LatLng location){
        this.latitude = location.latitude;
        this.longitude = location.longitude;
    }

    /**
     *
     * @return
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * @return
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     *
     * @return
     */
    public Location toAndroidLocation() {
        Location l = new Location("");
        l.setLatitude(latitude);
        l.setLongitude(longitude);
        return l;
    }

    /**
     *
     * @return
     */
    public LatLng toLatLng(){
        return new LatLng(latitude, longitude);
    }

    /**
     *
     * @param other
     * @return
     */
    public double distanceTo(QLocation other){
        return measure(getLatitude(), getLongitude(), other.getLatitude(), other.getLongitude()); //Math.sqrt(Math.pow(this.getLatitude() - other.latitude, 2) + Math.pow(this.getLongitude() - other.getLongitude(), 2));
    }

    public static double measure(double latitude1, double longitude1, double latitude2, double longitude2){  // generally used geo measurement function
        double R = 6378.137; // Radius of earth in KM
        double dLat = latitude2 * Math.PI / 180 - latitude1 * Math.PI / 180;
        double dLon = longitude2 * Math.PI / 180 - longitude1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(latitude1 * Math.PI / 180) *
                Math.cos(latitude2 * Math.PI / 180) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d * 1000; // meters
    }

    public double deltaLong(QLocation qLocation) {
        return qLocation.getLongitude() - this.getLongitude();
    }

    public double deltaLat(QLocation qLocation) {
        return qLocation.getLatitude() - this.getLatitude();
    }


    /**
     *
     * @param location
     * @return
     */
    public static LatLng convertToLatLng(Location location){
        return (new QLocation(location).toLatLng());
    }

    /**
     *
     * @param location
     * @return
     */
    public static Location convertToAndroidLocation(LatLng location){
        return (new QLocation(location)).toAndroidLocation();
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof QLocation){
            QLocation a = (QLocation) o;
            return a.getLongitude() == getLongitude() && a.getLatitude() == getLatitude();
        }
        return false;
    }

    /**
     *
     * @param in
     */
    protected QLocation(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    /**
     *  extenda javadoc
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     *  ectenda javadoc
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    /**
     *  extenda javadoc
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
