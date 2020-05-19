package com.example.App2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class WebsiteViewerActivity
        extends FragmentActivity
        implements TitlesFragment.ListSelectionListener {

    public static String[] mTitleArray;
    public static String[] mWebsiteArray;
    private Integer typeofactivity = 1;
    

    private final WebsiteFragment mWebsiteFragment = new WebsiteFragment();
    private TitlesFragment mTitlesFragment = null;
    private android.support.v4.app.FragmentManager mFragmentManager;

    private FrameLayout mTitleFrameLayout, mWebsiteFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "WebsiteViewerActivity";
    private int itemIndex;
    private int mShowIndex = -1;
    static String OLD_ITEM = "old_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        // Get the string arrays with the titles and websites
        Bundle extras = getIntent().getExtras();
        typeofactivity= extras.getInt("activitytype");

        
        if (typeofactivity == 1){
            mTitleArray = getResources().getStringArray(R.array.attractionTitles);
            mWebsiteArray = getResources().getStringArray(R.array.Attractions);
        }
        else{
            mTitleArray = getResources().getStringArray(R.array.restaurantTitles);
            mWebsiteArray = getResources().getStringArray(R.array.Restaurants);
        }


        setContentView(R.layout.main);

        // Get references to the TitleFragment and to the QuotesFragment
        mTitleFrameLayout = (FrameLayout) findViewById(R.id.title_fragment_container);
        mWebsiteFrameLayout = (FrameLayout) findViewById(R.id.website_fragment_container);


        mFragmentManager = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();
        mTitlesFragment = new TitlesFragment();
        mShowIndex = -1;

        fragmentTransaction.replace(
                R.id.title_fragment_container,
                mTitlesFragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager.addOnBackStackChangedListener(
                new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });

        if (savedInstanceState != null){

            mShowIndex = savedInstanceState.getInt(OLD_ITEM);

        }
    }

    private void setLayout() {

        // Determine whether the WebsiteFragment has been added
        if (!mWebsiteFragment.isAdded()|| mShowIndex ==-1) {

            // Make the TitleFragment occupy the entire layout
            mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {

            int orientation = this.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Log.i("Checked", "Orientation is portrait");
                mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 0f));

                mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));
            } else {
                Log.i("Checked", "Orientation is landscape");
                mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }
        }
    }

    // Implement Java interface ListSelectionListener defined in TitlesFragment
    // Called by TitlesFragment when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {


//        Log.i("Entered onlistselection","added");
        // If the WebsiteFragment has not been added, add it now
        if (!mWebsiteFragment.isAdded()) {

            // Start a new FragmentTransaction
            // UB 2/24/2019 -- Now must use compatible version of FragmentTransaction
            android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the QuoteFragment to the layout
            fragmentTransaction.add(R.id.website_fragment_container, mWebsiteFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (mWebsiteFragment.getShownIndex() != index) {

            // Tell the WebsiteFragment to show the quote string at position index
            mWebsiteFragment.showWebsiteAtIndex(index);
            mShowIndex = index;
            setLayout();

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentIndex",itemIndex);
        super.onSaveInstanceState(outState);
        if(mShowIndex >= 0){
            outState.putInt(OLD_ITEM,mShowIndex);

        }
        else{
            outState.putInt(OLD_ITEM,-1);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("entered here","restore");
        mShowIndex = savedInstanceState.getInt(OLD_ITEM);
        super.onRestoreInstanceState(savedInstanceState);
        setLayout();
        onListSelection(mShowIndex);

    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        Intent i = new Intent();
        if(id == R.id.attraction) {

            i.setClass(getApplicationContext(), WebsiteViewerActivity.class);
            i.putExtra("activitytype", 1);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        if(id == R.id.restaurant) {
            Log.i("option rest hit","tes");
            i.setClass(getApplicationContext(), WebsiteViewerActivity.class);
            i.putExtra("activitytype", 2);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    public void onStart() {

        super.onStart();
        if(mShowIndex>=0){
            mWebsiteFragment.showWebsiteAtIndex(mShowIndex);
            mTitlesFragment.setSelection(mShowIndex);
            mTitlesFragment.getListView().setItemChecked(mShowIndex,true);
        }
    }
}
