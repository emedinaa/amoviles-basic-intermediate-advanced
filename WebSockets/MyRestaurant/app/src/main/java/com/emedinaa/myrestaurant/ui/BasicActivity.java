package com.emedinaa.myrestaurant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public class BasicActivity extends AppCompatActivity{

    public void nextActivity(Intent intent,
                             @Nullable Bundle bundle, Boolean destroy){
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if(destroy){
            finish();
        }
    }

    public void  enableBack(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
       onBackPressed();
       return true;
    }
}
