package com.commonsware.empublite.prefs;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import com.commonsware.empublite.R;

import java.util.List;

/**
 * @author bamboo
 * @since 4/23/14 11:06 PM
 */
public class EditPreferencesActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.prefs_headers, target);
    }

    protected boolean isValidFragment(String fragmentName) {

        if (fragmentName.equals(First.class.getName())
                || fragmentName.equals(Second.class.getName())) {
            return true;
        }
        return false;
    }

    public static class First extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);

            final Preference p = findPreference("text");
            assert p != null;

            p.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    p.setSummary(newValue.toString());
                    return true;
                }
            });

        }
    }

    public static class Second extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs2);
        }


    }

}