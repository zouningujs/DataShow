package com.ujs.datashow2.preference;

import com.ujs.datashow2.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * 首选项
 * 
 * @author zouning
 * 
 */
public class ListPreference extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.list_preference);

        android.preference.ListPreference listPref = (android.preference.ListPreference) findPreference("listPreference");
        listPref.setEntryValues(new String[] { "2", "3", "4" });
        listPref.setEntries(new String[] { "Food", "Lounge",
                "Frequent Flier Program" });
    }
}
