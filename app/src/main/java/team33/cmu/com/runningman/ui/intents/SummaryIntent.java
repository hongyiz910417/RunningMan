package team33.cmu.com.runningman.ui.intents;

import android.content.Context;
import android.content.Intent;

/**
 * Hailun Zhu
 * ID: hailunz
 * Date: 11/13/15
 **/
public class SummaryIntent extends Intent {
    private Integer summaryId;

    public SummaryIntent(Context packageContext, Class<?> cls){
        super(packageContext, cls);
    }

    public void setSummaryId(Integer summaryId){
        this.summaryId = summaryId;
    }

    public Integer getSummaryId(){
        return summaryId;
    }
}
