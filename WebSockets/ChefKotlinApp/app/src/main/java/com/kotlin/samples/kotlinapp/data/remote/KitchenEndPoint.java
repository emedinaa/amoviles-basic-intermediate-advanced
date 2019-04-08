package com.kotlin.samples.kotlinapp.data.remote;

import com.kotlin.samples.kotlinapp.data.remote.model.LogInRaw;
import com.kotlin.samples.kotlinapp.data.remote.model.LogInResponse;
import com.kotlin.samples.kotlinapp.data.remote.model.PlateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public interface KitchenEndPoint {

    @POST("/auth/usuarios-login")
    Call<LogInResponse> logIn(@Body LogInRaw logInRaw);

    //https://diplomado-restaurant-backend.herokuapp.com/ordenes-de-compra
    @GET("/ordenes-de-compra")
    Call<PlateResponse> orders();
}

