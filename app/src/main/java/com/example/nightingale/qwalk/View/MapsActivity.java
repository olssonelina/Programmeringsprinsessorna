package com.example.nightingale.qwalk.View;

import android.Manifest;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nightingale.qwalk.InterfaceView.IMaps;
import com.example.nightingale.qwalk.Model.Android.QwalkMarker;
import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.Tiebreaker;
import com.example.nightingale.qwalk.Presenter.MapsPresenter;
import com.example.nightingale.qwalk.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.nightingale.qwalk.Model.IActor.NO_ANSWER;
import static java.lang.Math.abs;
import static java.lang.Math.sin;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCameraChangeListener,
        IMaps {

    private static final String TAG = MapsActivity.class.getSimpleName();
    public static final int ANSWER_CODE = 4331;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private MapsPresenter presenter;

    private TextView progress;
    private ImageView directionArrow;
    private ImageView bot;
    private Marker botMarker;
    private Button showClosest;

    private List<QwalkMarker> markers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progress = (TextView) findViewById(R.id.progress);

        directionArrow = (ImageView) findViewById(R.id.arrow);
        directionArrow.setImageResource(R.drawable.direction);
        showClosest = (Button) findViewById(R.id.viewPinButton);

        bot = (ImageView) findViewById(R.id.monkey);
        bot.setImageResource(R.drawable.monkey);

        presenter = new MapsPresenter(this, (Quiz) getIntent().getParcelableExtra("quiz")); // TODO hantera felet kanske


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        initializeFunctionality();
    }


    /**
     * This method is called whenever the location of the device is updated.
     * Checks distances to the location of questions and resets the map if necessary.
     * Also saves the current location.
     *
     * @param location The device's current location
     */
    @Override
    public void onLocationChanged(Location location) {
        presenter.updateUserLocation(new QLocation(location));
    }

    private void initializeFunctionality() {
        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraChangeListener(this);
        presenter.mapIsReady();
    }

    @Override
    public void focusOn(QLocation location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location.toLatLng(), 17));
    }

    @Override
    public void close() {
        finish();
    }

    /**
     * Checks if the marker for next question is on screen or not
     *
     * @param location Location of the next question
     * @return true if question marker is on screen, false if not
     */
    @Override
    public boolean isOnScreen(QLocation location) {
        LatLngBounds bounds = this.mMap.getProjection().getVisibleRegion().latLngBounds;

        if (!bounds.contains(new LatLng(location.getLatitude(), location.getLongitude()))) {
            return true;
        }
        return false;
    }

    /**
     * Points the direction arrow to the next question depending on angle.
     *
     * @param location Location of the next question
     */
    public void pointArrowTo(QLocation location) {

        //Latitude and longitude of screen center
        LatLng screenCenter = mMap.getCameraPosition().target;
        double screenCenterLat = screenCenter.latitude;
        double screenCenterLng = screenCenter.longitude;

        //Rotate direction arrow with the angle from the screen center to the next question
        int angle = angleToQuestion(screenCenterLat, screenCenterLng, location.getLatitude(), location.getLongitude());
        directionArrow.setRotation(angle);
        directionArrow.setVisibility(View.VISIBLE);

        //Place arrow in different positions on the screen depending on the angle to next question
        if ((angle >= 0 && angle <= 45) || (angle > 315 && angle < 360)) {
            directionArrow.setY(60);
            directionArrow.setX(screenWidth() / 2);
        }
        if (angle > 45 && angle <= 135) {
            directionArrow.setY(screenHeight() / 2 - 80);
            directionArrow.setX(screenWidth() - 110);
        }
        if (angle > 135 && angle <= 225) {
            directionArrow.setY(screenHeight() - 240);
            directionArrow.setX(screenWidth() / 2);
        }
        if (angle > 225 && angle <= 315) {
            directionArrow.setY(screenHeight() / 2 - 80);
            directionArrow.setX(60);
        }
    }

    @Override
    public void placeMarker(Question question) {
        for (QwalkMarker qm: markers) {
            if (qm.getQuestion().equals(question)){
                return;
            }
        }

        markers.add(new QwalkMarker(mMap, question));
    }

    @Override
    public void enableMarker(Question question) {
        markers.get(getQwalkMarkerIndex(question)).setEnabled();
    }

    @Override
    public void removeMarker(Question question) {
        int index = getQwalkMarkerIndex(question);
        markers.get(index).getMarker().remove();
        markers.remove(index);
    }

    @Override
    public void showResults(Quiz quiz, int[] playerAnswers, int[] aiAnswers, long quizTime) {
        Intent intent = new Intent(getBaseContext(), ShowResultActivity.class);
        intent.putExtra("player", playerAnswers);
        intent.putExtra("time", quizTime);
        intent.putExtra("quiz", quiz);
        if (aiAnswers != null){
            intent.putExtra("ai", aiAnswers);
        }

        startActivity(intent);
    }

    @Override
    public void setShowClosestEnabled(boolean value) {
        showClosest.setEnabled(value);
        showClosest.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void initializeAi(QLocation location) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location.toLatLng());
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.monkey));
        botMarker = mMap.addMarker(markerOptions);
    }

    @Override
    public void moveAi(QLocation location) {
        botMarker.setPosition(location.toLatLng());
    }

    public void viewPinButtonClicked(View view) {
        presenter.focusOnClosestQuestion();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!marker.equals(botMarker) && markers.get(getQwalkMarkerIndex(marker)).isEnabled()){

            Question currentQuestion = markers.get(getQwalkMarkerIndex(marker)).getQuestion();


            if (currentQuestion instanceof OptionQuestion) {
                Intent intent = new Intent(getBaseContext(), AnswerOptionActivity.class);
                intent.putExtra("question", (OptionQuestion) currentQuestion);
                intent.putExtra("questionIndex", presenter.getQuestionIndex(currentQuestion));

                if (presenter.hasAi()){
                    intent.putExtra("aiAnswer", presenter.getAiAnswer(currentQuestion));
                }
                else{
                    intent.putExtra("aiAnswer", NO_ANSWER);
                }

                startActivityForResult(intent, ANSWER_CODE);
            }

            if (currentQuestion instanceof Tiebreaker) {
                Intent intent = new Intent(getBaseContext(), AnswerTiebreakerActivity.class);
                intent.putExtra("question", (Tiebreaker) currentQuestion);
                startActivityForResult(intent, ANSWER_CODE);
            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ANSWER_CODE) {
            try {
                int answer = (int) data.getExtras().get("answer");
                Question question = (Question) data.getExtras().get("question");
                presenter.setAnswer(question, answer);
            } catch (NullPointerException e) { }
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    /**
     * Places an arrow on the screen which points in the direction of the
     * next question if it is offscreen.
     *
     * @param cameraPosition position of the center of the screen
     */

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        presenter.onCameraChanged();
    }


    private int screenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private int screenHeight() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    /**
     * Shows how many questions the player has answered of the total number of questions
     *
     * @param current Number of answered questions
     * @param total Number of total questions in current quiz
     */
    public void setProgress(int current, int total) {
        progress.setText(current + " av " + total);
    }

    /**
     * Hides direction arrow.
     */
    public void hideArrow() {
        directionArrow.setVisibility(View.INVISIBLE);
    }


    /**
     * Calculates the angle from the center of the screen (cameraPosition)
     * to the next question (mMarkerLocation).
     *
     * @param lat1  Latitude of x1
     * @param long1 Longitude of y1
     * @param lat2  Latitude of x2
     * @param long2 Longitude of y2
     * @return angle between two locations on the map
     */
    private int angleToQuestion(double lat1, double long1, double lat2,
                                double long2) {

        double dLon = (long2 - long1);

        double y = sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * sin(lat2) - sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;

        int angle = (int) brng;

        return angle;
    }

    private int getQwalkMarkerIndex(Question question){
        for (int i = 0; i < markers.size(); i++) {
            if (markers.get(i).getQuestion().equals(question)){
                return i;
            }
        }
        throw new IllegalArgumentException("No such marker in list!");
    }

    private int getQwalkMarkerIndex(Marker marker){
        for (int i = 0; i < markers.size(); i++) {
            if (markers.get(i).getMarker().equals(marker)){
                return i;
            }
        }
        throw new IllegalArgumentException("No such marker in list!");
    }

}
