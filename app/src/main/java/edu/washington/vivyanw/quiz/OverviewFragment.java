package edu.washington.vivyanw.quiz;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by WoodsFamily on 5/1/15.
 */
public class OverviewFragment extends Fragment {

    TextView descr;
    String topic;

    public OverviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        descr = (TextView) view.findViewById(R.id.txtDescr);
        if (topic.equalsIgnoreCase("math")) {
            descr.setText(R.string.math_overview);
        } else if (topic.equalsIgnoreCase("physics")) {
            descr.setText(R.string.physics_overview);
        } else if (topic.equalsIgnoreCase("marvel super heroes")) {
            descr.setText(R.string.marvel_overview);
        } else if (topic.equalsIgnoreCase("spongebob")) {
            descr.setText(R.string.sponge_overview);
        } else if (topic.equalsIgnoreCase("legend of zelda")) {
            descr.setText(R.string.zelda_overview);
        }

        return view;
    }

    public void setTopic(String tpc) {
        topic = tpc;
    }
}
