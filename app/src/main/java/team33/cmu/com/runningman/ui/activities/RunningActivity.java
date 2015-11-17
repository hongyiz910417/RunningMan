package team33.cmu.com.runningman.ui.activities;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import team33.cmu.com.runningman.R;
import team33.cmu.com.runningman.utils.GoogleMapUtils;


public class RunningActivity  extends FragmentActivity implements OnMapReadyCallback
        , ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    private static final int DEFAULT_ZOOM = 18;

    private static final int UPDATE_INTERVAL = 10000;

    private static final int FASTEST_UPDATE_INTERVAL = 5000;

    private static final int ROUTINE_WIDTH = 6;

    private static final int ROUTINE_COLOR = Color.RED;

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;

    private boolean started = false;

    private Location currentLocation;

    private List<Location> routine = new ArrayList<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.runningMap);
        mapFragment.getMapAsync(this);

        //set up GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        Button finishBtn = (Button) findViewById(R.id.runningFinishBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                started = false;
            }
        });

        Button startBtn = (Button) findViewById(R.id.runningStartBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                started = true;
            }
        });
    }

    protected LocationRequest createLocationRequest(){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    protected void updateRoutine(Location location){
        if(routine.size() == 0){
            routine.add(location);
            return;
        }

        Location prevLocation = routine.get(routine.size() - 1);
        PolylineOptions rectLine = new PolylineOptions().width(ROUTINE_WIDTH)
                .color(ROUTINE_COLOR);
        rectLine.add(new LatLng(prevLocation.getLatitude(), prevLocation.getLongitude()));
        rectLine.add(new LatLng(location.getLatitude(), location.getLongitude()));
        mMap.addPolyline(rectLine);
        routine.add(location);
    }

    protected void centerCamera(Location location, int zoom){
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
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
        mMap = GoogleMapUtils.setMap(mMap);
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            Location location = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            centerCamera(location, DEFAULT_ZOOM);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        centerCamera(currentLocation, DEFAULT_ZOOM);
        if(started){
            updateRoutine(currentLocation);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, createLocationRequest(), this);

        if(mMap != null){
            Location location = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            centerCamera(location, DEFAULT_ZOOM);
        }
    }

    @Override
    public void onConnectionSuspended(int cause){

    }

    @Override
    public void onConnectionFailed(ConnectionResult result){

    }
}
