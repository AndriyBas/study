package com.commonsware.empublite;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ModelFragment extends Fragment {
    private BookContents contents = null;
    private ContentsLoadTask contentsTask = null;

    private SharedPreferences mSharedPreferences = null;
    private PrefsLoadClass mPrefsLoadClass = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
        deliverModel();
    }

    synchronized private void deliverModel() {
        if (mSharedPreferences != null && contents != null) {
            ((EmPubLiteActivity) getActivity()).setupPager(mSharedPreferences, contents);
        } else {

            if (mSharedPreferences == null && mPrefsLoadClass == null) {

                mPrefsLoadClass = new PrefsLoadClass();
                executeAsyncTask(mPrefsLoadClass,
                        getActivity().getApplicationContext());

            }

            if (contents == null && contentsTask == null) {
                contentsTask = new ContentsLoadTask();
                executeAsyncTask(contentsTask,
                        getActivity().getApplicationContext());
            }
        }
    }

    @TargetApi(11)
    static public <T> void executeAsyncTask(AsyncTask<T, ?, ?> task,
                                            T... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

    private class ContentsLoadTask extends AsyncTask<Context, Void, Void> {
        private BookContents localContents = null;
        private Exception e = null;

        @Override
        protected Void doInBackground(Context... ctxt) {
            try {
                StringBuilder buf = new StringBuilder();
                InputStream json = ctxt[0].getAssets().open("book/contents.json");
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(json));
                String str;

                while ((str = in.readLine()) != null) {
                    buf.append(str);
                }

                in.close();

                localContents = new BookContents(new JSONObject(buf.toString()));
            } catch (Exception e) {
                this.e = e;
            }

            return (null);
        }

        @Override
        public void onPostExecute(Void arg0) {
            if (e == null) {
                ModelFragment.this.contents = localContents;
                ModelFragment.this.contentsTask = null;
                deliverModel();
            } else {
                Log.e(getClass().getSimpleName(), "Exception loading contents",
                        e);
            }
        }
    }

    private class PrefsLoadClass extends AsyncTask<Context, Void, Void> {

        private SharedPreferences mInnerPrefs;

        @Override
        protected Void doInBackground(Context... params) {

            mInnerPrefs = PreferenceManager.getDefaultSharedPreferences(params[0]);
            mInnerPrefs.getAll();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mSharedPreferences = mInnerPrefs;
            mPrefsLoadClass = null;

            deliverModel();
        }
    }


}
