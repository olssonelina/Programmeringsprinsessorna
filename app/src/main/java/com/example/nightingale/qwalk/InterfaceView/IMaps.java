package com.example.nightingale.qwalk.InterfaceView;

import com.example.nightingale.qwalk.Model.QLocation;

/**
 * Created by Kraft on 2017-05-12.
 */

public interface IMaps {
    boolean checkLocationPermission();
    void focusOn(QLocation location);
    void close();
    void isOnScreen(QLocation location);
    void showOnMap(); // Minns inte vad detta innebär längre
    void pointArrowTo(QLocation location);
    void placeMarker(QLocation location);
    void placeHiddenMarker(QLocation location);

}
