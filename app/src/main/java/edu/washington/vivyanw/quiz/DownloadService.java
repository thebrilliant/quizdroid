package edu.washington.vivyanw.quiz;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by WoodsFamily on 5/21/15.
 */
public class DownloadService extends IntentService {

    //fields
    DownloadManager mgr;
    public static final int ALARM = 42;
    String url;

    public DownloadService () {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        //String url = "http://www.userprefs.com/data.json";
        mgr = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        long enqueue = mgr.enqueue(request);
    }

    public void startOrStopAlarm(Context context, boolean on) {
        Log.i("DownloadService", "startOrStopAlarm on = " + on);

        Intent alarmReceiver = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM, alarmReceiver, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        url = context.getSharedPreferences("user_prefs.xml", 0).getString("userQ", "questions.json");

        if (on) {
            int mins = Integer.parseInt(context.getSharedPreferences("user_prefs.xml", 0).getString("checkForUpdates", "5"));
            int refreshInterval = mins * 60000; // 5 min x 60,000 milliseconds = total ms in 5 min

            Log.i("DownloadService", "setting alarm to " + refreshInterval);

            // Start the alarm manager to repeat
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), refreshInterval, pendingIntent);
        }
        else {
            manager.cancel(pendingIntent);
            pendingIntent.cancel();

            Log.i("DownloadService", "Stopping alarm");
        }
    }
}
