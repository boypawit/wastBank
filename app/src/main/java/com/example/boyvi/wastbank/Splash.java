package com.example.boyvi.wastbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;


public class Splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent LogginIntent = new Intent(Splash.this,Login.class);
                startActivity(LogginIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
