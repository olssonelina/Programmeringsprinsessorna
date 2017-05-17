package com.example.nightingale.qwalk.Model.Android;

import com.example.nightingale.qwalk.Model.Question;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraft on 2017-05-17.
 */

public class QwalkMarkerList {
    private List<Marker> markers = new ArrayList<>();
    private List<Boolean> enabled = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();

    public void add(GoogleMap map, Question question){
        if (contains(question)){
            return;
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(question.getLatitude(), question.getLongitude()));
        markerOptions.title("Nästa Fråga!"); // TODO lägg till i res
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        markers.add(map.addMarker(markerOptions));
        questions.add(question);
        enabled.add(false);
    }

    public boolean contains(Question question){
        return questions.contains(question);
    }

    public void enable(int index){
        markers.get(index).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        enabled.set(index, true);
    }

    public void enable(Question question){
        enable(getQuestionIndex(question));
    }

    public boolean isEnabled(Question question){
        return isEnabled(getQuestionIndex(question));
    }

    public boolean isEnabled(int index){
        return enabled.get(index);
    }

    public boolean isEnabled(Marker marker){
        return enabled.get(getMarkerIndex(marker));
    }

    public void remove(int index){
        markers.get(index).remove();
        markers.remove(index);
        enabled.remove(index);
        questions.remove(index);
    }

    public void remove(Question question){
        remove(getQuestionIndex(question));
    }

    public Question getQuestion(int index){
        return questions.get(index);
    }

    public Question getQuestion(Marker marker){
        return getQuestion(getMarkerIndex(marker));
    }

    private int getQuestionIndex(Question q) {
        for (int i = 0; i < questions.size(); i++) {
            if (q.equals(questions.get(i))) {
                return i;
            }
        }
        throw new IllegalArgumentException("No such question in list!");
    }

    private int getMarkerIndex(Marker m) {
        for (int i = 0; i < markers.size(); i++) {
            if (m.equals(markers.get(i))) {
                return i;
            }
        }
        throw new IllegalArgumentException("No such marker in list!");
    }

}
