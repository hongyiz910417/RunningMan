package team33.cmu.com.runningman.entities;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Team33
 * RunningMan
 * Date: 12/10/15
 **/

public class RunningSummaryUpdater implements SummaryUpdater {
    @Override
    public void updateSummary(Summary summary, LatLng newLocation, Date curTime) {
        LatLng prevLocation = summary.getRoute().get(summary.getRoute().size() - 1);
        float[] delta = new float[1];
        Location.distanceBetween(prevLocation.latitude, prevLocation.longitude
                , newLocation.latitude, newLocation.longitude, delta);
        summary.setDistance(delta[0] + summary.getDistance());
        summary.setEndDate(curTime);
        long duration = summary.getDuration();
        double pace = 1000d / (summary.getDistance() / (double)duration);
        summary.setPace(pace);
        summary.getRoute().add(newLocation);
    }

    @Override
    public void initSummary(Summary summary, LatLng startLocation, Date startTime){
        summary.setRoute(new ArrayList<LatLng>());
        summary.getRoute().add(startLocation);
        summary.setStartDate(startTime);
        summary.setEndDate(startTime);
        summary.setDistance(0);
        summary.setPace(0);
    }
}
