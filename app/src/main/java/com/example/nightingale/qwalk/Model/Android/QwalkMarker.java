package com.example.nightingale.qwalk.Model.Android;

import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Kraft on 2017-05-23.
 */

public class QwalkMarker {
    private Marker marker;
    private Question question;
    private boolean enabled;

    /**
     * Creates a new android marker for the Qwalk game
     *
     * @param map      instance of the current android map
     * @param question the question associated with this marker
     */
    public QwalkMarker(GoogleMap map, Question question) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(question.getLocation().toLatLng());
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.minimarkerblue));
        enabled = false;
        marker = map.addMarker(markerOptions);
        this.question = question;
    }

    /**
     * @return returns the Google Maps marker
     */
    public final Marker getMarker() {
        return marker;
    }

    /**
     * @return returns the associated question
     */
    public final Question getQuestion() {
        return question;
    }

    /**
     * @return true if this marker is within reach
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Enables the marker, it is in reach
     */
    public final void setEnabled() {
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.minimarkergreen));
        enabled = true;
    }
}
