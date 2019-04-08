package com.emedinaa.myrestaurant.data.remote.model;

import com.emedinaa.myrestaurant.model.entity.Dish;

import java.util.List;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public class DishesResponse extends BaseResponse {

    private List<Dish> data;

    public List<Dish> getData() {
        return data;
    }

    public void setData(List<Dish> data) {
        this.data = data;
    }
}
