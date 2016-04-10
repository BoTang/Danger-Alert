package com.example.winnie.myapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private Button EmergencyOn;
    private Button Setting;
    private Button exit;
    private Button Upload;

    private String msg;
    private String num;
    String phoneNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmergencyOn = (Button) findViewById(R.id.call);
        EmergencyOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                    if ("com.example.winnie.myapplication".equals(service.service.getClassName())) {
                        stopService(new Intent(MainActivity.this, LockService.class));
                        Toast toast = Toast.makeText(MainActivity.this, "Emergency Call service ends", Toast.LENGTH_SHORT);
                        toast.show();
                    } else startService(new Intent(MainActivity.this, LockService.class));
                    Toast toast = Toast.makeText(MainActivity.this, "Emergency Call service turned on", Toast.LENGTH_SHORT);
                    toast.show();

                }


            }
        });

        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                System.exit(0);
            }
        });

        Setting = (Button) findViewById(R.id.setting);
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Msg.class);
                startActivity(intent);
            }
        });




    }
}
