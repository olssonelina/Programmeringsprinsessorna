package com.example.nightingale.qwalk.Model.Android;

import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Kraft on 2017-05-23.
 */

public class QwalkMarker {
    private Marker marker;
    private Question question;
    private boolean enabled;

    public QwalkMarker(GoogleMap map, Question question){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(question.getLocation().toLatLng());
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.minimarkerblue));
        enabled = false;
        marker = map.addMarker(markerOptions);
        this.question = question;
    }


    public Marker getMarker() {
        return marker;
    }

    public Question getQuestion() {
        return question;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(){
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.minimarkergreen));
        enabled = true;
    }
}
