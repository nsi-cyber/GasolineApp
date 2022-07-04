package com.example.gasolineprices;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread=new Thread(){
            @Override
            public void run(){
                try{
                    sleep(3000);
                }catch(Exception e){
                    e.printStackTrace();}
                 finally{
                        startActivity(new Intent(Splash.this,ActivityLogin.class));
                    }}};
        thread.start();
    }}