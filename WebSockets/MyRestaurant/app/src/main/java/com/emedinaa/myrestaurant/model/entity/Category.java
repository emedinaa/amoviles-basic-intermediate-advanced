package com.emedinaa.myrestaurant.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public class Category implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("nombre")
    private String name;

    @SerializedName("descripcion")
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
