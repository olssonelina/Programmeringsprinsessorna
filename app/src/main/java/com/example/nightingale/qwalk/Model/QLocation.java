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
        return Math.sqrt(Math.pow(this.getLatitude() - other.latitude, 2) + Math.pow(this.getLongitude() - other.getLongitude(), 2));
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
