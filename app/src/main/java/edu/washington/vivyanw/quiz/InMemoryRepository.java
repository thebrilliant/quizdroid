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
    public void readJson(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        readTopicArray(reader);
    }

    public void readTopicArray(JsonReader reader) throws IOException {
        topics = new ArrayList<Topic>();
        reader.beginArray();
        while (reader.hasNext()) {
            topics.add(readTopic(reader));
        }
        reader.endArray();
    }

    public Topic readTopic(JsonReader reader) throws IOException {
        String descr;
        Topic temp = new Topic();

        reader.beginObject();
        while (reader.hasNext()) {
            String next = reader.nextName();
            if (next.equals("title")) {
                temp.setTitle(reader.nextString());
            } else if (next.equals("desc")) {
                descr = reader.nextString();
                temp.setDescrShort(descr);
                temp.setDescrLong(descr);
            } else if (next.equals("questions")) {
                temp.setQuestions(readQuestions(reader));
            } else {
                reader.skipValue();
            }
        }
        return temp;
    }

    public List<Quiz> readQuestions(JsonReader reader) throws IOException {
        List<Quiz> questions = new ArrayList<Quiz>();
        reader.beginArray();
        while (reader.hasNext()) {
            questions.add(readQ(reader));
        }
        reader.endArray();
        return questions;
    }

    public Quiz readQ(JsonReader reader) throws IOException {
        Quiz temp = new Quiz();

        reader.beginObject();
        while (reader.hasNext()) {
            String next = reader.nextName();
            if (next.equals("text")) {
                temp.setQuestion(reader.nextString());
            } else if (next.equals("answer")) {
                temp.setCorrect(reader.nextInt());
            } else if (next.equals("answers")) {
                readAnswers(reader, temp);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return temp;
    }

    public void readAnswers(JsonReader reader, Quiz question) throws IOException {
        List<String> ans = new ArrayList<String>();

        reader.beginArray();
        while (reader.hasNext()) {
            ans.add(reader.nextString());
        }
        reader.endArray();

        question.setAns1(ans.get(0));
        question.setAns2(ans.get(1));
        question.setAns3(ans.get(2));
        question.setAns4(ans.get(3));
    }

    @Override
    public void readJsonText(String json) throws JSONException {
        JSONArray data = new JSONArray(json);
        topics = new ArrayList<Topic>();

        for (int i = 0; i < data.length(); i++) {
            topics.add(readJsonObject(data.getJSONObject(i)));
        }

        /*String title = "empty";
        String desc = "empty";
        /*while (data.has("title")) {
            title = data.getString("title");
            desc = data.getString("desc");
        }

        Log.i("InMemoryRepository", "title: " + title + " desc: " + desc);*/
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
