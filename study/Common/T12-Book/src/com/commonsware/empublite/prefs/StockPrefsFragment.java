package com.commonsware.empublite.prefs;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * @author bamboo
 * @since 4/24/14 1:26 AM
 */
public class StockPrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int res = getActivity()
                .getResources()
                .getIdentifier(getArguments().getString("resource"),
                        "xml",
                        getActivity().getPackageName());


        addPreferencesFromResource(res);
    }
}
