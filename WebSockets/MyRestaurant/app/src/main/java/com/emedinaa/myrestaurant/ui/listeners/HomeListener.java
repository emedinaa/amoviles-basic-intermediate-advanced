package com.emedinaa.myrestaurant.ui.listeners;

import android.support.annotation.NonNull;

import com.emedinaa.myrestaurant.model.entity.Category;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/11/18
 */
public interface HomeListener {

    @NonNull Category currentCategory();
    void selectCategory(@NonNull Category category);
}
