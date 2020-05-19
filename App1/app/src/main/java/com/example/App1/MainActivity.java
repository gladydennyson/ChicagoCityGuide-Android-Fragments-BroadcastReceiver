package com.example.App1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    Button attractionBtn,restaurantBtn;
    private int buttonId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         attractionBtn = (Button) findViewById(R.id.attraction);
         restaurantBtn = (Button) findViewById(R.id.restaurant);

         attractionBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 buttonId = 1;
                 checkPermissionBroadcast();


             }
         });

         restaurantBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 buttonId = 2;
                 checkPermissionBroadcast();


             }
         });
    }

    public void checkPermissionBroadcast(){

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                "edu.uic.cs478.sp.project3") !=
                PackageManager.PERMISSION_GRANTED) {

            //If permission is not granted, request this permission from the user
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{"edu.uic.cs478.sp.project3"},
                    0);

        }
        else {
            Intent intent = new Intent("com.example.gladydennysonthomas_project3_app1");

            if(buttonId ==1){
                // If attractions button is selected
                Toast.makeText(getApplicationContext(),"Attractions button selected, sending broadcast to App2", Toast.LENGTH_SHORT).show();
            }
            else{
                // If restaurants button is selected
                Toast.makeText(getApplicationContext(),"Restaurants button selected, sending broadcast to App2", Toast.LENGTH_SHORT).show();

            }
            intent.putExtra("button id",buttonId);
            // sending broadcast to app2
            sendBroadcast(intent);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("com.example.gladydennysonthomas_project3_app1");
                    if(buttonId ==1){
                        // If attractions button is selected
                        Toast.makeText(getApplicationContext(),"Attractions button selected, sending broadcast to app2", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // If restaurants button is selected
                        Toast.makeText(getApplicationContext(),"Restaurants button selected, sending broadcast to app2", Toast.LENGTH_SHORT).show();

                    }
                    intent.putExtra("button id",buttonId);
                    sendBroadcast(intent);
                }
                else {
                    Toast.makeText(this, "No permission granted", Toast.LENGTH_LONG).show() ;
                }
            }

        }
    }


}

