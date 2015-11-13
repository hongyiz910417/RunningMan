package team33.cmu.com.runningman.entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by d on 11/13/15.
 */
interface SummaryUpdater {
    /**
     * update the summary(including pace, duration) by adding the newLocation along with the date
     * @param summary
     * @param newLocation
     * @param curTime
     */
    public void UpdateSummary(Summary summary, LatLng newLocation, Date curTime);
}
