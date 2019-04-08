package com.emedinaa.core.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/11/18
 */
public abstract class BasicActivity extends AppCompatActivity {

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

    protected void showMessage(@NonNull String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
