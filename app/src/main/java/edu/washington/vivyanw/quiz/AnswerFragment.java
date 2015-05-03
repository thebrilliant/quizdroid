package edu.washington.vivyanw.quiz;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by WoodsFamily on 5/1/15.
 */
public class AnswerFragment extends Fragment {

    TextView myAnswer;
    TextView theAnswer;
    TextView counter;

    String answer;
    String rightAnswer;
    int correct;
    int numQ;

    public AnswerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.answer_fragment, container, false);
        myAnswer = (TextView) rootView.findViewById(R.id.myAnswer);
        theAnswer = (TextView) rootView.findViewById(R.id.rightAnswer);
        counter = (TextView) rootView.findViewById(R.id.rightSoFar);

        if (answer.equals(rightAnswer)) {
            myAnswer.setTextColor(Color.argb(255, 0, 71, 4));
        }
        myAnswer.setText("Your answer: " + answer);
        theAnswer.setText("Correct answer: " + rightAnswer);
        counter.setText("You have " + correct + " out of " + numQ + " correct!");
        return rootView;
    }

    public void setFields(String ans, String crctAns, int numC, int numberQ) {
        answer = ans;
        rightAnswer = crctAns;
        correct = numC;
        numQ = numberQ;
    }
}