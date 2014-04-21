package com.oyster.Alpha;

import android.app.Fragment;

public class AlphaActivity extends SingleFragmentActivity {


    @Override
    public Fragment getFragment() {
        return new SensorFragment();
    }

}
