package com.example.App2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;


public class WebsiteFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "WebsiteFragment";

    private TextView mQuoteView = null;
    private int mCurrIdx = -1;
    private int mWebArrLen;
    private String[] websites;
    private WebView mwebView;
    int getShownIndex() {
        return mCurrIdx;
    }

    // Show the Website  at position newIndex
    void showWebsiteAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mWebArrLen)
            return;

        mCurrIdx = newIndex;
        websites = WebsiteViewerActivity.mWebsiteArray;

        ((WebView) (getView().findViewById(R.id.webView))).loadUrl(websites[mCurrIdx]);

    }

    @Override
    public void onAttach(Context activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");

        // Inflate the layout defined in website_fragment.xml

        return inflater.inflate(R.layout.website_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        mWebArrLen = WebsiteViewerActivity.mWebsiteArray.length;
    }





}
