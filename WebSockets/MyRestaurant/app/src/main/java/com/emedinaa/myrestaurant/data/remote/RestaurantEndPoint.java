package com.emedinaa.myrestaurant.data.remote;

import com.emedinaa.myrestaurant.data.remote.model.CategoriesResponse;
import com.emedinaa.myrestaurant.data.remote.model.CategoryResponse;
import com.emedinaa.myrestaurant.data.remote.model.DishResponse;
import com.emedinaa.myrestaurant.data.remote.model.DishesResponse;
import com.emedinaa.myrestaurant.data.remote.model.LogInRaw;
import com.emedinaa.myrestaurant.data.remote.model.LogInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public interface RestaurantEndPoint {

    //@GET("/categoria")
    @GET("/categorias")
    Call<CategoriesResponse> categories();

    @GET("/categorias/{id}")
    Call<CategoryResponse> category(@Path("id")String id);

    @GET("/platos")
    Call<DishesResponse> dishes();

    @GET("/platos/categoria/{id}")
    Call<DishesResponse> dishesByCategory(@Path("id")String id);

    @GET("/platos/{id}")
    Call<DishResponse> dish(@Path("id")String id);

    //@POST("/auth/usuarios-login")
    //Call<LogInResponse> logIn(@Body LogInRaw logInRaw);

    @POST("/auth/clientes-login")
    Call<LogInResponse> logIn(@Body LogInRaw logInRaw);


    //web token

    @GET("/api/categorias")
    Call<CategoriesResponse> categoriesWT(@Header("Authorization") String authorization);
}

