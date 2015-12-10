package team33.cmu.com.runningman.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by d on 11/18/15.
 */
public class OutputFormat {
    private static NumberFormat formatter = new DecimalFormat("#0.00");

    private static String formatSeconds(int seconds){
        int minutes = seconds / 60;
        seconds = seconds % 60;
        String str = String.format("%d\"%02d'", minutes, seconds);
        return str;
    }

    public static String formatPace(double pace){
        StringBuilder sb = new StringBuilder();
        sb.append(formatSeconds((int)pace));
        sb.append(" /km");
        return sb.toString();
    }

    public static String formatDuration(int seconds){
        return formatSeconds(seconds);
    }

    public static String formatDistance(double distance){
        double distInKm = distance / 1000d;
        String str = formatter.format(distInKm);
        return str + " km";
    }

    public static String formatDate(Date date){
        SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
        return dt1.format(date);
    }
}
