package com.emedinaa.core.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/11/18
 */
public abstract class BasicFragment extends Fragment {

    protected void showMessage(@NonNull String message){
        Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();
    }

    public void nextActivity(Intent intent,
                             @Nullable Bundle bundle, Boolean destroy){
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if(destroy && getActivity()!=null){
            getActivity().finish();
        }
    }

    protected void setCustomTitle(@NonNull String title){
        getActivity().setTitle(title);
    }
}
