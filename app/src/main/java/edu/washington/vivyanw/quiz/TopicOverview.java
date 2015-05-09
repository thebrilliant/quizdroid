package edu.washington.vivyanw.quiz;

/**
 * Created by WoodsFamily on 4/26/15.
 */

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class TopicOverview extends ActionBarActivity {

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_one_layout);

        Intent launchingIntent = getIntent();
        final String topic = launchingIntent.getStringExtra("topic");
        final int numQ = launchingIntent.getIntExtra("numQ", 0);

        TextView title = (TextView) findViewById(R.id.txtTitle);
        title.setText(topic);

        TextView descr = (TextView) findViewById(R.id.txtDescr);
        descr.setText("This is a quiz about " + topic + "!  It will ask trivia questions about "
                      + topic + " to test your skills! There are but " + numQ + " questions.");

        submit = (Button) findViewById(R.id.btnGo);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(TopicOverview.this, Question.class);
                next.putExtra("topic", topic);
                next.putExtra("numQ", numQ);
                next.putExtra("answered", 0);

                Log.i("TopicOverview", "firing intent" + next);
                startActivity(next);

                //finish();
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
