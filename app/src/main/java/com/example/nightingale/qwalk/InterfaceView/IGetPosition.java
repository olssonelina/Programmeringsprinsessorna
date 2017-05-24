package com.example.nightingale.qwalk.InterfaceView;

/**
 * Created by Kraft on 2017-05-10.
 */

public interface IGetPosition {
    boolean checkLocationPermission();

    double getMarkerLatitude();

    double getMarkerLongitude();

    void moveMarker(double latitude, double longitude);

    void focusOn(double latitude, double longitude);

    void stopLocationUpdates();

    void closeWithResult(double latitude, double longitude);

    void setDoneButtonEnabled(boolean enabled);
}
