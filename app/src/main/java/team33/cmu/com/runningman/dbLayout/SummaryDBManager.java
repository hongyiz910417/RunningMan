package team33.cmu.com.runningman.dbLayout;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

import team33.cmu.com.runningman.entities.Summary;

/**
 * Created by d on 11/13/15.
 */
public class SummaryDBManager {
    public void insertSummary(Summary summary){
        System.out.println("summary saved");
    }
    
    public Summary getSummaryByName(String summaryName){
        // TODO: 11/13/15 
        return null;
    }
    
    public Summary getSummaryById(Integer summaryId){
        //stub
        Summary summary = new Summary(1234, new ArrayList<LatLng>(), "test", 25.3, 17.5, new Date()
                , new Date());
        return summary;
    }
    
    public void deleteSummaryByName(String summaryName){
        // TODO: 11/13/15
    }
    
    public void deleteSummaryById(String summaryId){
        // TODO: 11/13/15
    }
}
