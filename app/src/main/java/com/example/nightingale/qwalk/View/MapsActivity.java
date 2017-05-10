package com.example.nightingale.qwalk.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.Tiebreaker;
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
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCameraChangeListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    private Location mLastLocation;
    private Location mMarkerLocation;
    private Marker mMarker;

    public final static int QUESTION_RANGE = 25;
    private boolean inQuestionRange = false;
    private Quiz currentQuiz;
    private Question currentQuestion;

    ImageView goHere;

    ImageView directions[] = new ImageView[8];

    int angle = 90;
    int pivotX = 30;
    int pivotY = 10;

    public static final int ANSWER_CODE = 4331;


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


        goHere = (ImageView) findViewById(R.id.goHere);
        goHere.setImageResource(R.drawable.up);

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

        if (mMarker == null) {
            resetMap(location);
            nextQuestion();
        }

        mLastLocation = location;

        if (location.distanceTo(mMarkerLocation) < QUESTION_RANGE) {
            mMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            inQuestionRange = true;
        }

        /*//stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }*/

    }

    private void resetMap(Location currentLocation) {
        inQuestionRange = false;
        LatLng latitudeLongitude = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latitudeLongitude));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        currentQuiz = getIntent().getParcelableExtra("quiz");
    }

    private void nextQuestion() {
        inQuestionRange = false; // Reset the inRange boolean
        if (currentQuestion == null) { // Start quiz
            currentQuestion = currentQuiz.get(0);
            showQuestionOnMap(currentQuestion);
            return;
        } else {
            if (currentQuestion.getLatitude() == currentQuiz.get(currentQuiz.size() - 1).getLatitude()) { // End quiz
                //TODO det som ska hända när ett quiz är klart
                finish();
            } else { // Continue quiz by figuring out which the next question is
                for (int i = 0; i < currentQuiz.size() - 1; i++) {
                    if (currentQuestion.getLatitude() == currentQuiz.get(i).getLatitude()) { //TODO det borde vara en korrekt equalsmetod
                        currentQuestion = currentQuiz.get(i + 1);
                        showQuestionOnMap(currentQuestion);
                        return;
                    }
                }
            }
        }
    }

    private void showQuestionOnMap(Question question) {
        if (mMarker == null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(question.getLatitude(), question.getLongitude()));
            markerOptions.title("OptionQuestion");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMarker = mMap.addMarker(markerOptions);
        } else {
            mMarker.setPosition(new LatLng(question.getLatitude(), question.getLongitude()));
            mMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        }
        mMarkerLocation = question.getLocation();
        ;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMarker.getPosition(), 17));


    }

    public void viewPinButtonClicked(View view) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMarker.getPosition(), 17));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(mMarker) && inQuestionRange) {

            if (currentQuestion instanceof OptionQuestion) {
                Intent intent = new Intent(getBaseContext(), AnswerOptionActivity.class);
                intent.putExtra("question", (OptionQuestion) currentQuestion);
                startActivityForResult(intent, ANSWER_CODE);
            }

            if (currentQuestion instanceof Tiebreaker) {
                Intent intent = new Intent(getBaseContext(), AnswerTiebreakerActivity.class);
                intent.putExtra("question", (Tiebreaker) currentQuestion);
                startActivityForResult(intent, ANSWER_CODE);
            }

        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ANSWER_CODE) {
            int answer = (int) data.getExtras().get("answer");

            //TODO hantera resultatet

            Toast.makeText(this, "Chosen answer: " + answer, Toast.LENGTH_LONG).show();
            nextQuestion();


        }
    }
    //region Hidden code


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

        mMap.setOnMarkerClickListener(this);

        mMap.setOnCameraChangeListener(this);

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


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        LatLngBounds bounds = this.mMap.getProjection().getVisibleRegion().latLngBounds;
        LatLng screenCenter = mMap.getCameraPosition().target;

        double screenCenterLng = screenCenter.longitude;
        double screenCenterLat = screenCenter.latitude;

        //If the question pin is offscreen, an arrow will show up and point in the direction of the question
        if (!bounds.contains(new LatLng(mMarkerLocation.getLatitude(), mMarkerLocation.getLongitude()))) {
            int angle = angleToQuestion(screenCenterLat, screenCenterLng, mMarkerLocation.getLatitude(), mMarkerLocation.getLongitude());
            goHere.setRotation(angle);
            goHere.setVisibility(View.VISIBLE);

            goHere.setY(780);
            goHere.setX(480);

        } else {
            goHere.setVisibility(View.INVISIBLE);

        }
    }


    private int angleToQuestion(double lat1, double long1, double lat2,
                                double long2) {

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;

        int angle = (int) brng;

        return angle;
    }


//endregion
}
