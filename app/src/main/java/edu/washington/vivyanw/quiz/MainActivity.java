package edu.washington.vivyanw.quiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import edu.washington.vivyanw.quiz.TopicOverview;


public class MainActivity extends ActionBarActivity {

    String[] items = {"Math", "Physics", "Marvel Super Heroes", "Spongebob", "Legend of Zelda"};
    public static final String topicTag = "topic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView topics = (ListView) findViewById(R.id.lstTopics);
        topics.setAdapter(adapter);
        topics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Clicked Topic Number " + position, Toast.LENGTH_LONG).show();
                String topic = items[position];
                int numQ;
                if (topic.equalsIgnoreCase("math") || topic.equalsIgnoreCase("physics")) {
                    numQ = 2;
                } else {
                    numQ = 3;
                }

                Intent next = new Intent(MainActivity.this, TopicOverview.class);
                next.putExtra(topicTag,topic);
                //next.putExtra("numQuestions", numQ);
                next.putExtra("numQ", numQ);

                Log.i("MainActivity", "firing intent" + next);
                startActivity(next);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
