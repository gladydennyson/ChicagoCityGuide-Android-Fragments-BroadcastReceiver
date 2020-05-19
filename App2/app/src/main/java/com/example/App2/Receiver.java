package com.example.App2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Receiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("button id");

//        Log.i("id is"+id,"result is"+result);

        Intent i = new Intent();
        if(id == 1){
            i.setClass(context.getApplicationContext(), WebsiteViewerActivity.class);
//            Toast.makeText(context, "Received broadcast"+id, Toast.LENGTH_LONG).show();
            i.putExtra("activitytype",1);
            context.startActivity(i);

        }
        else{
            i.setClass(context.getApplicationContext(), WebsiteViewerActivity.class);
//            Toast.makeText(context, "Received broadcast"+id, Toast.LENGTH_LONG).show();
            i.putExtra("activitytype",2);
            context.startActivity(i);
        }

    }
}
