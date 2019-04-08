package com.emedinaa.myrestaurant.data.remote.model;

import com.emedinaa.myrestaurant.model.entity.Category;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public class CategoryResponse extends BaseResponse {

    private Category data;

    public Category getData() {
        return data;
    }

    public void setData(Category data) {
        this.data = data;
    }
}
