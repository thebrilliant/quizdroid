package edu.washington.vivyanw.quiz;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.prefs.PreferenceChangeEvent;

/**
 * Created by WoodsFamily on 5/15/15.
 */
public class UserPreferencesActivity extends PreferenceActivity {
    static boolean isSet = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_prefs);

        //PreferenceChangeEvent prefs = new PreferenceChangeEvent(R.xml.user_prefs, "userQ", );
        //new PreferenceChangeEvent()
        isSet = true;
    }
}
