package com.emedinaa.myrestaurant.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public class Dish implements Serializable{

    @SerializedName("_id")
    private String id;

    @SerializedName("idCategoria")
    private String idCategory;

    @SerializedName("nombre")
    private String name;

    @SerializedName("precio")
    private String price;

    @SerializedName("descripcion")
    private String desc;

    @SerializedName("imagen")
    private String photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
