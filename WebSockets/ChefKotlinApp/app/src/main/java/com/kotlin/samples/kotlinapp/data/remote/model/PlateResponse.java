package com.kotlin.samples.kotlinapp.data.remote.model;


import com.kotlin.samples.kotlinapp.model.Order;

import java.util.List;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/18/18
 */
public class PlateResponse extends BaseResponse {
    private List<Order> data;

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}
