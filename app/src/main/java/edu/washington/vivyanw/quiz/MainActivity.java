package edu.washington.vivyanw.quiz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static final int SETTINGS = 1;

    String downloadURL;
    int interval;

    TopicRepository quiz;
    List<Topic> topicItems;
    QuizApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApp = (QuizApp) getApplication();
        quiz = myApp.quiz;

        if (new UserPreferencesActivity().isSet) {
            checkForUpdates();
        }
        topicItems = quiz.getAllTopics();

        ArrayAdapter<Topic> adapter = new ArrayAdapter<Topic>(this, android.R.layout.simple_list_item_1, topicItems);
        ListView topics = (ListView) findViewById(R.id.lstTopics);
        topics.setAdapter(adapter);
        topics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent next = new Intent(MainActivity.this, QuizActivity.class);
                next.putExtra("topic", position);

                Log.i("MainActivity", "firing intent" + next);
                startActivity(next);
            }
        });
    }

    public void checkForUpdates() {
        //final SharedPreferences prefs = getSharedPreferences("user_prefs.xml", 0);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(MainActivity.this, downloadURL, Toast.LENGTH_SHORT).show();
            }
        };
        registerReceiver(alarmReceiver, new IntentFilter("edu.washington.vivyanw.updates"));
        Intent i = new Intent();
        i.setAction("edu.washington.vivyanw.updates");
        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 5000, interval * 1000 * 60,
                        PendingIntent.getBroadcast(MainActivity.this, 0, i, 0));
    }

    private void openPrefs() {
        Intent i = new Intent(getApplicationContext(), UserPreferencesActivity.class);
        startActivityForResult(i, SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SETTINGS) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

            downloadURL = sharedPrefs.getString("userQ", "questions.json");
            String minute = sharedPrefs.getString("checkForUpdates", "0");
            if (minute.equals("15")) {
                interval = 15;
            } else if (minute.equals("30")) {
                interval = 30;
            } else if (minute.equals("60")) {
                interval = 60;
            } else if (minute.equals("120")) {
                interval = 120;
            } else if (minute.equals("240")) {
                interval = 240;
            } else {
                interval = 0;
            }

            myApp.updateLocation = downloadURL;
            myApp.updateInterval = interval;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                openPrefs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
