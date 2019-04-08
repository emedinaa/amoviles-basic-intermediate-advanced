package com.emedinaa.myrestaurant;

import android.content.Intent;
import android.os.Bundle;

import com.emedinaa.myrestaurant.ui.BasicActivity;
import com.emedinaa.myrestaurant.ui.DashboardActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BasicActivity {

    private static final long SPLASH_SCREEN_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                goToMain();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    private void goToMain(){
        nextActivity(new Intent(this, DashboardActivity.class),null,
                true);
    }
}
