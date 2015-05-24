package edu.washington.vivyanw.quiz;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.*;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.widget.*;
import android.view.*;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static final int SETTINGS = 1;

    String downloadURL;
    int interval;

    TopicRepository quiz;
    List<Topic> topicItems;
    QuizApp myApp;

    DownloadManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApp = (QuizApp) getApplication();
        quiz = myApp.quiz;

        //if (new UserPreferencesActivity().isSet) {
            checkForUpdates();
        //}

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
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE); // Add more filters here that you want the receiver to listen to
        registerReceiver(receiver, filter);
        Intent i = new Intent();
        i.setAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 5000, interval * 1000 * 60,
                PendingIntent.getBroadcast(MainActivity.this, 0, i, 0));

    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            ConnectivityManager cmgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cmgr.getActiveNetworkInfo();

            if (networkInfo == null) {
                Toast.makeText(MainActivity.this, "You are not connected to the internet!", Toast.LENGTH_LONG).show();
                int airplaneOn = Settings.System.getInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 1);
                if (airplaneOn == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Airplane Mode On");
                    builder.setMessage("Airplane mode is turned on for your device. Do you want to turn it off?");
                    builder.setPositiveButton("Yes please", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent next = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                            startActivity(next);
                        }
                    });
                    builder.create().show();
                }
            } else if (networkInfo.isConnected()) {

                dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                Log.i("Main BroadcastReceiver", "onReceive of registered download receiver");

                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    Log.i("Main BroadcastReceiver", "download complete!");
                    long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                    // if the downloadID exists
                    if (downloadID != 0) {

                        // Check status
                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(downloadID);
                        Cursor c = dm.query(query);
                        if (c.moveToFirst()) {
                            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                            Log.d("DM Sample", "Status Check: " + status);
                            switch (status) {
                                case DownloadManager.STATUS_PAUSED:
                                case DownloadManager.STATUS_PENDING:
                                case DownloadManager.STATUS_RUNNING:
                                    break;
                                case DownloadManager.STATUS_SUCCESSFUL:
                                    Log.i("Main BR", "something was successful!");
                                    // The download-complete message said the download was "successful"
                                    ParcelFileDescriptor file;
                                    StringBuffer strContent = new StringBuffer("");

                                    try {
                                        // Get file from Download Manager
                                        file = dm.openDownloadedFile(downloadID);
                                        FileInputStream fis = new FileInputStream(file.getFileDescriptor());

                                        // YOUR CODE HERE [convert file to String here]
                                        String json = myApp.readJSONFile(fis);
                                        //quiz.readJsonText(json);


                                        // YOUR CODE HERE [write string to data/data.json]
                                        myApp.writeFile(json);

                                        // convert your json to a string and echo it out here to
                                        // show that you did download it
                                        String proofDownload= myApp.readJSONFile(getAssets().open("questions.json"));

                                        /*
                                        String jsonString = ....myjson...to string().... chipotle burritos.... blah
                                        Log.i("MyApp - Here is the json we download:", jsonString);
                                        */

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    //} catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case DownloadManager.STATUS_FAILED:
                                    //download has failed!
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setTitle("Download Failed!!");
                                    builder.setMessage("The questions failed to download, do you want to retry now or later?");
                                    builder.setPositiveButton("Let's try again...", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            checkForUpdates();
                                        }
                                    });
                                    builder.setNegativeButton("Quit!", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            onDestroy();
                                        }
                                    });

                                    break;
                            }
                        }
                    } else {
                        Log.d("Main BR", "The download doesn't exist! Oooh...scary");
                    }
                } else {

                }
            }
        }
    };

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
            interval = Integer.parseInt(minute);

            myApp.updateLocation = downloadURL;
            myApp.updateInterval = interval;
        }
        checkForUpdates();
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