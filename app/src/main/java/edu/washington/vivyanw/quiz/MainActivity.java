package edu.washington.vivyanw.quiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.os.Build;
import android.widget.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    String[] items = {"Math", "Physics", "Marvel Super Heroes", "Spongebob", "Legend of Zelda"};
    String[] shortDesc = {"2 high level math Q", "physics theory questions", "superhero trivia",
                          "some spongebob trivia", "the legend begins"};
    TopicRepository quiz;
    List<Topic> topicItems;
    public static final String topicTag = "topic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuizApp myApp = (QuizApp) getApplication();
        quiz = myApp.quiz;

        topicItems = quiz.getAllTopics();

        ArrayAdapter<Topic> adapter = new ArrayAdapter<Topic>(this, android.R.layout.simple_list_item_1, topicItems);
        ListView topics = (ListView) findViewById(R.id.lstTopics);
        topics.setAdapter(adapter);
        topics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = topicItems.get(position).getTitle();
                //Toast.makeText(MainActivity.this, "Selected " + title + ": " + position, Toast.LENGTH_SHORT).show();

                Intent next = new Intent(MainActivity.this, QuizActivity.class);
                next.putExtra("topic", position);
                //next.putExtra(topicTag, title);
                //next.putExtra("numQ", topicItems.get(position).getQuestions().size());

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
