package com.example.winnie.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Winnie on 4/9/2016.
 */
public class ShowLock extends AppCompatActivity implements LocationListener {
    public String msg = "";
    public String num = "";
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    String provider;
    protected boolean gps_enabled, network_enabled;
    double latitude;
    double longitude;
    String str = "";

    private Button unlockButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lock);

        unlockButton = (Button) findViewById(R.id.unlock);

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ShowLock.this.finish();
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        longitude = location.getLongitude();
        latitude=location.getLatitude();
        LatLng place = new LatLng(latitude, longitude);


        try{
            Geocoder geo = new Geocoder(ShowLock.this, Locale.getDefault());

            List<Address> addresses = geo.getFromLocation(latitude,longitude, 1);
            str="";
            if (addresses.isEmpty()) {

            }
            else {
                if (addresses.size() > 0) {

                    for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++) {
                        str = str + addresses.get(0).getAddressLine(index);
                    }


                }
            }
        }
        catch (IOException i)
        {}


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    /*public void onAttachedToWindow() {
        // TODO Auto-generated method stub
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        super.onAttachedToWindow();
    }*/

    //使back键，音量加减键失效

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return disableKeycode(keyCode, event);
    }

    private boolean disableKeycode(int keyCode, KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_MENU:
            case KeyEvent.KEYCODE_SEARCH:

                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:

                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String time= sDateFormat.format(new java.util.Date());
                //String Date = sDateFormat.format(new java.util.Date());
                //sDateFormat = new SimpleDateFormat("hh:mm:ss");
                //String Time = sDateFormat.format(new java.util.Date());
                File dir=new File(path);
                dir.mkdirs();
                String log =time+","+latitude+","+longitude+","+str+";";
                File eventfile = new File(path + "/log.txt");
                String[] savelog = String.valueOf(log).split(System.getProperty("line.separator"));
                Save(eventfile, savelog);
                //latitude,longitude,str
                //放入文件









                File msgfile = new File(path + "/1.txt");
                File numfile = new File(path + "/2.txt");
                if(msgfile.exists()&&numfile.exists()) {
                    String[] loadText = Load(msgfile);
                    String[] loadnum = Load(numfile);
                    for (int i = 0; i < loadText.length; i++) {
                        msg += loadText[i] + System.getProperty("line.separator");
                        num += loadnum[i] + System.getProperty("Line.separator");
                    }
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(num, null, msg+"Sent from"+str, null, null);

                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + "12408934406");
                    intent.setData(data);


                    startActivity(intent);


                    return true;
                }
                else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + "12408934406");
                    intent.setData(data);


                    startActivity(intent);
                }




                return true;
        }
        return  true;
    }




    public static String[] Load(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (Exception e) {

        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int i = 0;
        try {
            while ((test = br.readLine()) != null) {
                i++;
            }
        } catch (Exception e) {

        }
        try {
            fis.getChannel().position(0);
        } catch (Exception e) {

        }
        String[] array = new String[i];
        String line;
        int j = 0;
        try {
            while ((line = br.readLine()) != null) {
                array[j] = line;
                j++;
            }
        } catch (Exception e) {

        }
        return array;
    }
    public static void Save(File file, String data[]) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (Exception e) {

        }
        try {
            try{
                for(int i=0;i<data.length;i++){
                    fos.write(data[i].getBytes());
                    if(i<data.length-1){
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (Exception e){

            }
            finally {
                try{
                    fos.close();
                }
                catch (Exception e){

                }
            }
        } catch (Exception e) {

        }


    }
}


