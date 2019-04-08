package com.emedinaa.myrestaurant.data.remote.model;

import com.emedinaa.myrestaurant.model.entity.Dish;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/10/18
 */
public class DishResponse extends BaseResponse {
    private Dish data;

    public Dish getData() {
        return data;
    }

    public void setData(Dish data) {
        this.data = data;
    }
}
