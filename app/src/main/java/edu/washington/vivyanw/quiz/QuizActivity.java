package edu.washington.vivyanw.quiz;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;


/**
 * Created by WoodsFamily on 5/1/15.
 */
public class QuizActivity extends ActionBarActivity {

    OverviewFragment ovrvwFragment;
    QuestionFragment questFragment;
    AnswerFragment ansFragment;

    TextView title;
    Button next;

    String topic;
    int numQ;
    int qAnswered;
    String answer;
    String rightAnswer;
    int correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_layout);

        Intent launchingIntent = getIntent();
        topic = launchingIntent.getStringExtra("topic");
        numQ = launchingIntent.getIntExtra("numQ", 0);
        qAnswered = 0;
        correct = 0;

        ovrvwFragment = new OverviewFragment();
        ovrvwFragment.setTopic(topic);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                    ft.add(R.id.container, ovrvwFragment);
                    ft.addToBackStack("Overview");
                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    ft.commit();
        }

        title = (TextView) findViewById(R.id.txtTitle);
        title.setText(topic);

        next = (Button) findViewById(R.id.btnGo);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (next.getText().equals("Begin") || next.getText().equals("Next")) {
                    questFragment = new QuestionFragment();
                    questFragment.setFields(topic, qAnswered, correct);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, questFragment)
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .addToBackStack("Question")
                            .commit();
                    next.setText(R.string.submit);
                    qAnswered++;
                } else if (next.getText().equals("Submit")) {
                    answer = questFragment.getAnswer();
                    rightAnswer = questFragment.getRightAnswer();
                    correct = questFragment.getCorrect();
                    ansFragment = new AnswerFragment();
                    ansFragment.setFields(answer, rightAnswer, correct, qAnswered);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, ansFragment)
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .commit();
                    if (qAnswered == numQ) {
                        next.setText(R.string.end);
                    } else {
                        next.setText(R.string.next);
                    }
                } else if (next.getText().equals("Finish")) {
                    Intent next = new Intent(QuizActivity.this, MainActivity.class);
                    startActivity(next);
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
