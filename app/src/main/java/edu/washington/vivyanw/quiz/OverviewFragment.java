package edu.washington.vivyanw.quiz;

//import android.app.Fragment;
import android.app.Fragment;
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
    String description;

    public OverviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        topic = getArguments().getString("topic");
        description = getArguments().getString("descr");

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        descr = (TextView) view.findViewById(R.id.txtDescr);
        descr.setText(description);

        return view;
    }
}
