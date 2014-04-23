package com.commonsware.empublite.prefs;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.commonsware.empublite.R;

import java.util.List;

/**
 * @author bamboo
 * @since 4/24/14 1:30 AM
 */
public class DisplayPreferenceActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            addPreferencesFromResource(R.xml.book_display_prefs);
        }

    }

    protected boolean isValidFragment(String fragmentName) {
        if (fragmentName.equals(StockPrefsFragment.class.getName())) {
            return true;
        }
        return false;
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.book_display_headers, target);
    }
}
