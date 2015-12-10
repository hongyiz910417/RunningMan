package team33.cmu.com.runningman.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import team33.cmu.com.runningman.R;
import team33.cmu.com.runningman.dbLayout.SummaryDBManager;
import team33.cmu.com.runningman.entities.Summary;
import team33.cmu.com.runningman.ui.intents.HomeViewIntent;
import team33.cmu.com.runningman.utils.GoogleMapUtils;
import team33.cmu.com.runningman.utils.OutputFormat;

/**
 * Team33
 * RunningMan
 * Date: 12/10/15
 **/

public class SummaryActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final int ROUTINE_WIDTH = 6;

    private static final int ROUTINE_COLOR = Color.RED;

    private static final int DEFAULT_ZOOM = 18;

    private Summary summary = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.summaryMap);
        mapFragment.getMapAsync(this);

        Button returnBtn = (Button) findViewById(R.id.summaryReturnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeViewIntent intent = new HomeViewIntent(SummaryActivity.this
                        , HomeViewActivity.class);
                startActivity(intent);
            }
        });

        final Intent intent = getIntent();

        AsyncTask<Object, Object, Object> dbTask =
                new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object... params) {
                        SummaryDBManager summaryDBManager = new SummaryDBManager();
                        try {
                            summary = summaryDBManager.getSummaryById(intent.getIntExtra("id", -1));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        if(mMap != null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateUI(summary);
                                }
                            });
                        }
                        return null;
                    } // end method doInBackground

                    @Override
                    protected void onPostExecute(Object result) {
                    } // end method onPostExecute//
                }; // end AsyncTask

        // save the contact to the database using a separate thread
        dbTask.execute((Object[]) null);
    }

    private void updateUI(Summary summary){
        //draw route on map
        PolylineOptions rectLine = new PolylineOptions().width(ROUTINE_WIDTH)
                .color(ROUTINE_COLOR);
        List<LatLng> routes = summary.getRoute();
        for(LatLng latLng : routes) {
            rectLine.add(latLng);
        }
        mMap.addPolyline(rectLine);
        //focus on the route
        if(routes.size() > 0){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(routes.get(0)
                    , DEFAULT_ZOOM));
        }

        //update labels
        String distStr = OutputFormat.formatDistance(summary.getDistance());
        String paceStr = OutputFormat.formatPace(summary.getPace());
        String durationStr = OutputFormat.formatDuration((int)summary.getDuration());
        ((TextView) findViewById(R.id.summaryNameValue)).setText(summary.getName());
        ((TextView) findViewById(R.id.summaryDistanceValue)).setText(distStr);
        ((TextView)findViewById(R.id.summaryPaceValue)).setText(paceStr);
        ((TextView) findViewById(R.id.summaryDurationValue)).setText(durationStr);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GoogleMapUtils.setMap(mMap);
        if(summary != null){
            updateUI(summary);
        }
    }
}
