package team33.cmu.com.runningman.entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Team33
 * RunningMan
 * Date: 12/10/15
 **/
public interface SummaryUpdater {
    /**
     * update the summary(including pace, duration) by adding the newLocation along with the date
     * @param summary
     * @param newLocation
     * @param curTime
     */
    public void updateSummary(Summary summary, LatLng newLocation, Date curTime);

    public void initSummary(Summary summary, LatLng startLocation, Date startTime);
}
