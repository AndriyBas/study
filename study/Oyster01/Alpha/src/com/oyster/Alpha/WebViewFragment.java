package com.oyster.Alpha;

import android.app.Fragment;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Date;

/**
 * @author bamboo
 * @since 3/5/14 5:19 PM
 */
public class WebViewFragment extends Fragment {

    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);

        mWebView = (WebView) v.findViewById(R.id.webView);
        mWebView.setWebViewClient(new Callbaks());

        loadTime();

        return v;
    }

    private void loadTime() {

//        mWebView.loadUrl("about:blank");
        String page =
                "<html><body><a href='clock'>"
                        + DateUtils.formatDateTime(getActivity(), new Date().getTime(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME)
                        + "</a></body></html>";
//        mWebView.loadDataWithBaseURL(null, page, "", "UTF-8", "");
        mWebView.loadData(page, "text/html", "UTF-8");
    }

    private class Callbaks extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            loadTime();
            return false;
        }
    }

}