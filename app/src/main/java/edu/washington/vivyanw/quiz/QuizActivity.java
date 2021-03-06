package edu.washington.vivyanw.quiz;

import android.content.Intent;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.List;

public class QuizActivity extends ActionBarActivity {

    public static final int SETTINGS = 1;

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

    int topicPos;
    TopicRepository quiz;
    Topic quizTopic;
    List<Quiz> questions;
    Quiz currentQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_layout);
        QuizApp myApp = (QuizApp) getApplication();
        quiz = myApp.quiz;

        Intent launchingIntent = getIntent();
        topicPos = launchingIntent.getIntExtra("topic", 0);
        quizTopic = quiz.getAllTopics().get(topicPos);
        topic = quizTopic.getTitle();
        questions = quizTopic.getQuestions();
        numQ = questions.size();

        ovrvwFragment = new OverviewFragment();

        if (savedInstanceState == null) {
            Bundle info = new Bundle();
            info.putString("topic", topic);
            info.putString("descr", quizTopic.getDescrLong());
            ovrvwFragment.setArguments(info);
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                    .add(R.id.container, ovrvwFragment)
                    .addToBackStack("Overview")
                    .commit();
        }

        title = (TextView) findViewById(R.id.txtTitle);
        title.setText(topic);

        next = (Button) findViewById(R.id.btnGo);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (next.getText().equals("Begin") || next.getText().equals("Next")) {
                    currentQ = questions.get(qAnswered);
                    Bundle fields = new Bundle();
                    fields.putString("question", currentQ.getQuestion());
                    fields.putString("opt1", currentQ.getAns1());
                    fields.putString("opt2", currentQ.getAns2());
                    fields.putString("opt3", currentQ.getAns3());
                    fields.putString("opt4", currentQ.getAns4());
                    questFragment = new QuestionFragment();
                    questFragment.setArguments(fields);
                    next.setVisibility(View.INVISIBLE);
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                            .replace(R.id.container, questFragment)
                            .addToBackStack("Question")
                            .commit();
                    next.setText(R.string.submit);
                    qAnswered++;
                } else if (next.getText().equals("Submit")) {
                    answer = questFragment.getAnswer(); //gets answer that user chose

                    //sets the right answer to the correct option
                    //depending on the current question
                    int rightOpt = currentQ.getCorrect();
                    if (rightOpt == 1) {
                        rightAnswer = currentQ.getAns1();
                    } else if (rightOpt == 2) {
                        rightAnswer = currentQ.getAns2();
                    } else if (rightOpt == 3) {
                        rightAnswer = currentQ.getAns3();
                    } else {
                        rightAnswer = currentQ.getAns4();
                    }

                    if (answer.equals(rightAnswer)) {
                        correct++;
                    }
                    Bundle fields = new Bundle();
                    fields.putInt("correct", correct);
                    fields.putInt("answered", qAnswered);
                    fields.putString("right", rightAnswer);
                    fields.putString("ans", answer);
                    ansFragment = new AnswerFragment();
                    ansFragment.setArguments(fields);
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                            .replace(R.id.container, ansFragment)
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

    private void openPrefs() {
        Intent i = new Intent(getApplicationContext(), UserPreferencesActivity.class);
        startActivityForResult(i, SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SETTINGS) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

            String downloadURL = sharedPrefs.getString("userQ", "questions.json");
            String minute = sharedPrefs.getString("checkForUpdates", "0");
            int interval;
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

            QuizApp app = (QuizApp) getApplication();
            app.updateLocation = downloadURL;
            app.updateInterval = interval;
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