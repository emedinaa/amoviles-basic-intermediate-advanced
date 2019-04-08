package com.emedinaa.myrestaurant.ui.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.emedinaa.myrestaurant.R;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public class ChefCustomView extends LinearLayout {

    public ChefCustomView(Context context) {
        super(context);
        setUp(context);
    }

    public ChefCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUp(context);
    }

    public ChefCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context);
    }

    private void setUp(Context context){

        LayoutInflater inflater= LayoutInflater.from(context);
        View childView= inflater.inflate(R.layout.layout_chef_view,null,false);
        childView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        this.addView(childView);
    }
}
