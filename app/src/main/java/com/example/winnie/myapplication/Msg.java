package com.example.winnie.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Winnie on 4/9/2016.
 */
public class Msg extends AppCompatActivity {
    private Button submit;
    private Button back;
    private EditText msgtext;
    private EditText numtext;

    private String msg;
    private String num;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg);
        File dir=new File(path);
        dir.mkdirs();



        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText msgtext = (EditText) findViewById(R.id.msg);

                EditText numtext = (EditText) findViewById(R.id.num);
                msg = msgtext.getText().toString();
                num = numtext.getText().toString();
                File msgfile = new File(path + "/1.txt");
                File numfile = new File(path + "/2.txt");
                String[] savemsg = String.valueOf(msg).split(System.getProperty("line.separator"));

                Save(msgfile, savemsg);
                String[] savenum = String.valueOf(num).split(System.getProperty("line.separator"));
                Save(numfile, savenum);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(Msg.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
