package edu.washington.vivyanw.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.*;

/**
 * Created by WoodsFamily on 4/27/15.
 */
public class Answer extends ActionBarActivity {

    //fields
    String topic;
    String answer;
    String correctAnswer;
    int numCorrect;
    int numQ;
    int answered;

    TextView title;
    TextView myAnswer;
    TextView rightAnswer;
    TextView counter;

    Button nextQ;
    boolean end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_layout);

        title = (TextView) findViewById(R.id.txtTitle);
        myAnswer = (TextView) findViewById(R.id.myAnswer);
        rightAnswer = (TextView) findViewById(R.id.rightAnswer);
        counter = (TextView) findViewById(R.id.rightSoFar);
        nextQ = (Button) findViewById(R.id.btnNext);

        Intent launchingIntent = getIntent();
        topic = launchingIntent.getStringExtra("topic");
        numQ = launchingIntent.getIntExtra("numQ", 2);
        answered = launchingIntent.getIntExtra("answered", 0);
        numCorrect = launchingIntent.getIntExtra("correct", 0);
        answer = launchingIntent.getStringExtra("choice");
        correctAnswer = launchingIntent.getStringExtra("right");

        title.setText(topic);
        if (answer.equalsIgnoreCase(correctAnswer)) {
            myAnswer.setTextColor(Color.argb(255, 0, 71, 4));
        }
        myAnswer.setText("Your answer: " + answer);
        rightAnswer.setText("Correct answer: " + correctAnswer);
        counter.setText("You have " + numCorrect + " out of " + answered + " correct!");


        if (numQ == answered) {
            nextQ.setText("Finish");
            end = true;
        }

        nextQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next;
                if (end) {
                    next = new Intent(Answer.this, MainActivity.class);
                } else {
                    next = new Intent(Answer.this, Question.class);
                    next.putExtra("topic", topic);
                    next.putExtra("numQ", numQ);
                    next.putExtra("answered", answered);
                    next.putExtra("correct", numCorrect);
                }

                startActivity(next);

                if (end) {
                    finish();
                }
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
