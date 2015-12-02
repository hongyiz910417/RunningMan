package team33.cmu.com.runningman.ui.activities;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import java.util.Date;

import team33.cmu.com.runningman.R;
import team33.cmu.com.runningman.dbLayout.SummaryDBManager;
import team33.cmu.com.runningman.entities.RunningSummaryUpdater;
import team33.cmu.com.runningman.entities.Summary;
import team33.cmu.com.runningman.entities.SummaryUpdater;
import team33.cmu.com.runningman.ui.dialogs.NewRunNameDialog;
import team33.cmu.com.runningman.ui.intents.HomeViewIntent;
import team33.cmu.com.runningman.utils.GoogleMapUtils;
import team33.cmu.com.runningman.utils.OutputFormat;


public class RunningActivity  extends FragmentActivity implements OnMapReadyCallback
        , ConnectionCallbacks, OnConnectionFailedListener, LocationListener
        , NewRunNameDialog.NewRunNameListener {
    private static final int DEFAULT_ZOOM = 18;

    private static final int UPDATE_INTERVAL = 1000;

    private static final int FASTEST_UPDATE_INTERVAL = 1000;

    private static final int ROUTINE_WIDTH = 6;

    private static final int ROUTINE_COLOR = Color.RED;

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;

    private volatile boolean started = false;

    private Thread timerThread;

    private Location currentLocation;

    private Summary summary;

    private SummaryUpdater summaryUpdater = new RunningSummaryUpdater();

    private SummaryDBManager summaryDBManager = new SummaryDBManager();

    private Button finishBtn;

    private Button startBtn;

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

        finishBtn = (Button) findViewById(R.id.runningFinishBtn);
        startBtn = (Button) findViewById(R.id.runningStartBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                started = false;
                startBtn.setEnabled(true);
                NewRunNameDialog namingDialog = new NewRunNameDialog();
                namingDialog.show(getFragmentManager(), "newRunNameDialog");
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBtn.setEnabled(false);
                started = true;
                mMap.clear();
                summary = new Summary();
                summaryUpdater.initSummary(summary
                        , new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())
                        , new Date());

                //thread for counting time
                timerThread = new Thread() {
                    private long seconds = 0;

                    @Override
                    public void run() {
                        while (started) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String durationStr = OutputFormat.formatDuration((int) seconds);
                                    ((TextView) findViewById(R.id.runningDurationValue))
                                            .setText(durationStr);
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            seconds++;
                        }
                    }
                };
                timerThread.start();
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

    protected void updateTracking(Location location){
        summaryUpdater.updateSummary(summary
                , new LatLng(location.getLatitude(), location.getLongitude()), new Date());

        updateStatistics();

        LatLng prevLatLng = summary.getRoute().get(summary.getRoute().size() - 2);
        PolylineOptions rectLine = new PolylineOptions().width(ROUTINE_WIDTH)
                .color(ROUTINE_COLOR);
        rectLine.add(prevLatLng);
        rectLine.add(new LatLng(location.getLatitude(), location.getLongitude()));
        mMap.addPolyline(rectLine);
    }

    protected void updateStatistics(){
        String distStr = OutputFormat.formatDistance(summary.getDistance());
        String paceStr = OutputFormat.formatPace(summary.getPace());
        ((TextView) findViewById(R.id.runningDistanceValue)).setText(distStr);
        ((TextView)findViewById(R.id.runningPaceValue)).setText(paceStr);
    }

    @Override
    public void onFinishNewRunNameDialog(String name){
        summary.setName(name);
        summaryDBManager.insertSummary(summary);
        HomeViewIntent myIntent = new HomeViewIntent(RunningActivity.this,
                HomeViewActivity.class);
        startActivity(myIntent);
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
        mMap.moveCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            Location location = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude())
                    , DEFAULT_ZOOM));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        if(mMap != null && mGoogleApiClient.isConnected()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(
                    new LatLng(location.getLatitude(), location.getLongitude())));
        }
        if(started){
            updateTracking(currentLocation);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, createLocationRequest(), this);

        if(mMap != null){
            Location location = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude())
                    , DEFAULT_ZOOM));
        }
    }

    @Override
    public void onConnectionSuspended(int cause){

    }

    @Override
    public void onConnectionFailed(ConnectionResult result){

    }
}
