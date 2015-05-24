package edu.washington.vivyanw.quiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by WoodsFamily on 5/21/15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiver", "entered onReceive() from AlarmReceiver");

        Intent download = new Intent(context, DownloadService.class);
        download.putExtra("url", "user prefs url");
        context.startService(download);
    }
}
