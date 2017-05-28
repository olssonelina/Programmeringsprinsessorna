package com.example.nightingale.qwalk.Presenter.GetPosition;

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
        view.setDoneButtonEnabled(false);
    }

    public final boolean isMarkerPlaced() {
        return isMapReady && userLatitude != 0 && userLongitude != 0;
    }

    public final void setMapReady() {
        isMapReady = true;
        tryPlaceMarker();
    }

    public final void setUserLocation(double userLatitude, double userLongitude){
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        tryPlaceMarker();
    }

    private void tryPlaceMarker(){
        if (isMapReady && userLatitude != 0 && userLongitude != 0){
            view.moveMarker(userLatitude, userLongitude);
            view.focusOn(userLatitude, userLongitude);
            view.stopLocationUpdates();
            view.setDoneButtonEnabled(true);
        }
    }

    public final void mapClicked(double latitude, double longitude){
        view.moveMarker(latitude, longitude);
    }

    public final void closeButtonPressed(){
        view.closeWithResult(view.getMarkerLatitude(), view.getMarkerLongitude());
    }
}
