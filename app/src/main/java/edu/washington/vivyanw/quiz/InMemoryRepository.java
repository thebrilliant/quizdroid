package edu.washington.vivyanw.quiz;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WoodsFamily on 5/8/15.
 */
public class InMemoryRepository implements TopicRepository {

    String[] items = {"Math", "Physics", "Marvel Super Heroes", "Spongebob", "Legend of Zelda"};
    private List<Topic> topics;

    public InMemoryRepository() {
        /*topics = new ArrayList<Topic>();
        for (int i = 0; i < items.length; i++) {
            Topic temp = new Topic();
            temp.setTitle(items[i]);
            temp.setDescrShort("none");
            topics.add(temp);
        }*/
    }

    public InMemoryRepository(String[] titles, String[] descr) {
        topics = new ArrayList<Topic>();
        for (int i = 0; i < titles.length; i++) {
            Topic temp = new Topic();
            temp.setTitle(titles[i]);
            temp.setDescrShort(descr[i]);
            topics.add(temp);
        }

        setQandA();
    }

    private void setQandA() {
        for (int i = 0; i < topics.size(); i++) {
            Topic temp = topics.get(i);
            temp.setDescrLong(temp.getDescrShort());
            List<Quiz> questions = new ArrayList<Quiz>();
            Quiz q1 = new Quiz();
            q1.setQuestion("pi is 3.141592...., what is e?");
            q1.setAns1("2");
            q1.setAns2("2.718281828");
            q1.setAns3("10");
            q1.setAns4("1.6180339887");
            q1.setCorrect(2);
            Quiz q2 = new Quiz();
            q2.setQuestion("what is the derivative of e ^ x?");
            q2.setAns1("e");
            q2.setAns2("e ^ x + C");
            q2.setAns3("e ^ x");
            q2.setAns4("e ^ (1/x)");
            q2.setCorrect(3);
            questions.add(q1);
            questions.add(q2);
            temp.setQuestions(questions);
        }
    }

    @Override
    public void readJsonText(String json) throws JSONException {
        JSONArray data = new JSONArray(json);
        topics = new ArrayList<Topic>();

        for (int i = 0; i < data.length(); i++) {
            topics.add(readJsonObject(data.getJSONObject(i)));
        }
    }

    public Topic readJsonObject(JSONObject topic) throws JSONException {
        String title = topic.getString("title");
        String desc = topic.getString("desc");
        Topic newTopic = new Topic();
        newTopic.setTitle(title);
        newTopic.setDescrShort(desc);
        newTopic.setDescrLong(desc);
        JSONArray question = topic.getJSONArray("questions");
        newTopic.setQuestions(readQuestionArray(question));
        return newTopic;
    }

    public List<Quiz> readQuestionArray(JSONArray array) throws JSONException{
        List<Quiz> result = new ArrayList<Quiz>();
        for (int i = 0; i < array.length(); i++) {
            result.add(readQuestion(array.getJSONObject(i)));
        }

        return result;
    }

    public Quiz readQuestion(JSONObject object) throws JSONException {
        Quiz question = new Quiz();
        question.setQuestion(object.getString("text"));
        question.setCorrect(object.getInt("answer"));
        JSONArray answers = object.getJSONArray("answers");
        question.setAns1(answers.getString(0));
        question.setAns2(answers.getString(1));
        question.setAns3(answers.getString(2));
        question.setAns4(answers.getString(3));
        return question;
    }

    @Override
    public List<Topic> getAllTopics() {
        return topics;
    }

    @Override
    public List<Topic> getTopicsByKeyword(String keyword) {
        List<Topic> result = new ArrayList<Topic>();
        for (int i = 0; i < topics.size(); i++) {
            Topic temp = topics.get(i);
            if (temp.getTitle().contains(keyword)) {
                result.add(temp);
            }
        }
        return result;
    }
}
