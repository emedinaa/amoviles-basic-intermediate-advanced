package com.emedinaa.myrestaurant.data.remote.model;

import com.emedinaa.myrestaurant.model.entity.Category;

import java.util.List;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public class CategoriesResponse extends BaseResponse {

    private List<Category> data;

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
}
