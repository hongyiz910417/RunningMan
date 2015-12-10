package team33.cmu.com.runningman.local;

/**
 * Hailun Zhu
 * ID: hailunz
 * Date: 12/10/15
 **/
// BackgroundService.java

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import team33.cmu.com.runningman.R;
import team33.cmu.com.runningman.ui.activities.HomeViewActivity;

public class NotificationService extends Service {
    private NotificationManager notificationMgr;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("fresh");
        notificationMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        displayNotificationMessage("starting Background Service");
        Thread thr = new Thread(null, new ServiceWorker(), "BackgroundService");
        thr.start();
    }

    class ServiceWorker implements Runnable {
        public void run() {
            // do background processing here...
            // stop the service when done...
            // BackgroundService.this.stopSelf();
        }
    }

    @Override
    public void onDestroy() {
        displayNotificationMessage("stopping Background Service");
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private void displayNotificationMessage(String message) {
        System.out.println("display");
        Intent resultIntent = new Intent(this, HomeViewActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("RunningMan")
                        .setContentText("Ladder refreshed!");
        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());


    }

}