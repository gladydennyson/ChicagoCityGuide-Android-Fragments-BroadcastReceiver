package com.example.App2;

//import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private BroadcastReceiver mReceiver ;
    private IntentFilter mFilter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReceiver = new Receiver();

        mFilter = new IntentFilter();
        mFilter.addAction("com.example.gladydennysonthomas_project3_app1");

        //Registering the receiver
        registerReceiver(mReceiver,mFilter,"edu.uic.cs478.sp.project3",null) ;

    }
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
            i.setClass(getApplicationContext(), WebsiteViewerActivity.class);
            i.putExtra("activitytype", 2);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
