package com.oyster.Alpha;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * @author bamboo
 * @since 3/3/14 11:02 PM
 */
public abstract class SingleFragmentActivity extends Activity {


    public abstract Fragment getFragment();

    protected int getContainerId() {
        return R.id.fragmentContainer;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(getContainerId());
        frameLayout.setLayoutParams(
                new ViewGroup.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT));

        setContentView(frameLayout);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(getContainerId());

        if (fragment == null) {

            fragment = getFragment();
            fm.beginTransaction()
                    .add(getContainerId(), fragment)
                    .commit();
        }
    }
}
