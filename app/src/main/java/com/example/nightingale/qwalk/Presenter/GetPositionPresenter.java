package com.example.nightingale.qwalk.Presenter;

import com.example.nightingale.qwalk.InterfaceView.IGetPosition;

/**
 * Created by Kraft on 2017-05-10.
 */

public class GetPositionPresenter {

    private IGetPosition view;
    private boolean isMapReady = false;
    private double userLatitude = 0;
    private double userLongitude = 0;

    public GetPositionPresenter(IGetPosition view){
        this.view = view;
        view.checkLocationPermission();
    }

    public boolean isMarkerPlaced() {
        return isMapReady && userLatitude != 0 && userLongitude != 0;
    }

    public void setMapReady() {
        isMapReady = true;
        tryPlaceMarker();
    }

    public void setUserLocation(double userLatitude, double userLongitude){
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        tryPlaceMarker();
    }

    private void tryPlaceMarker(){
        if (isMapReady && userLatitude != 0 && userLongitude != 0){
            view.moveMarker(userLatitude, userLongitude);
            view.focusOn(userLatitude, userLongitude);
            view.stopLocationUpdates();
        }
    }

    public void mapClicked(double latitude, double longitude){
        view.moveMarker(latitude, longitude);
    }

    public void closeButtonPressed(){
        view.closeWithResult(view.getMarkerLatitude(), view.getMarkerLongitude());
    }
}
