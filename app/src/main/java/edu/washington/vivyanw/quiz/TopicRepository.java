package edu.washington.vivyanw.quiz;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by WoodsFamily on 5/8/15.
 */
public interface TopicRepository {

    public List<Topic> getAllTopics();

    public List<Topic> getTopicsByKeyword(String keyword);

    public void readJson(InputStream in) throws IOException;

    public void readJsonText(String jSon) throws JSONException;

    //public Topic readTopic() throws IOException;
}
