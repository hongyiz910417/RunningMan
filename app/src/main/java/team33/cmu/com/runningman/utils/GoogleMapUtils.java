package team33.cmu.com.runningman.utils;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by d on 11/16/15.
 */
public class GoogleMapUtils {
    /**
     * initialize google map
     * @param map
     * @return
     */
    public static GoogleMap setMap(GoogleMap map){
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
        return map;
    }
}
