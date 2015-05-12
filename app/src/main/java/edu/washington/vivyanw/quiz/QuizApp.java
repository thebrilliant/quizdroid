package edu.washington.vivyanw.quiz;

import android.app.Application;
import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by WoodsFamily on 5/8/15.
 */
public class QuizApp extends Application {
    static String[] items = {"Math", "Physics", "Marvel Super Heroes", "Spongebob", "Legend of Zelda"};
    static String[] shortDesc = {"2 high level math Q", "physics theory questions", "superhero trivia",
            "some spongebob trivia", "the legend begins"};

    private static QuizApp instance = null;
    private static String tag = "QuizApp";
    public static TopicRepository quiz = new InMemoryRepository();

    public QuizApp() throws IOException {
        //ensures that there is only one instance of QuizApp
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Cannot create more than one QuizApp");
        }

    }

    public String readJSONFile(InputStream in) throws IOException {
        int length = in.available();
        byte[] buffer = new byte[length];
        in.read(buffer);
        in.close();

        return new String(buffer, "UTF-8");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            InputStream inputStream = getAssets().open("questions.json");
            String jSon = readJSONFile(inputStream);
            quiz.readJsonText(jSon);
        } catch (IOException error) {
            Log.e(tag, "Couldn't open file", error);
            error.printStackTrace();
        } catch (JSONException error) {
            Log.e(tag, "Could not read JSON", error);
            error.printStackTrace();
        }

        Log.d(tag,"QuizApp loaded and running");
    }
}

//Quiz object
class Quiz {

    private String question;
    private String ans1;
    private String ans2;
    private String ans3;
    private String ans4;
    private int correct;

    public Quiz() {

    }

    //setter for question
    public void setQuestion(String question) {
        this.question = question;
    }

    //getter for question
    public String getQuestion() {
        return question;
    }

    //getters and setters for the different answers
    public void setAns1(String ans) {
        ans1 = ans;
    }

    public void setAns2(String ans) {
        ans2 = ans;
    }

    public void setAns3(String ans) {
        ans3 = ans;
    }

    public void setAns4(String ans) {
        ans4 = ans;
    }

    public String getAns1() {
        return ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public String getAns4() {
        return ans4;
    }

    //sets the number for which answer is correct
    public void setCorrect(int right) {
        correct = right;
    }

    //returns the number of which answer was right
    public int getCorrect() {
        return correct;
    }
}


//Topic object
class Topic {

    private String title;
    private String descrShort;
    private String descrLong;
    private List<Quiz> questions;

    public Topic() {

    }

    public String toString() {
        return title;
    }

    //sets the title
    public void setTitle(String title) {
        this.title = title;
    }

    //returns the topic title
    public String getTitle() {
        return title;
    }

    //description getters and setters
    public void setDescrShort(String descr) {
        descrShort = descr;
    }

    public String getDescrShort() {
        return descrShort;
    }

    public void setDescrLong(String descr) {
        descrLong = descr;
    }

    public String getDescrLong() {
        return descrLong;
    }

    //sets the list of questions for the topic
    public void setQuestions(List<Quiz> quest) {
        questions = quest;
    }

    //returns the list of questions for the topic
    public List<Quiz> getQuestions() {
        return questions;
    }
}