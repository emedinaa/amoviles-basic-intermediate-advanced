package com.emedinaa.myrestaurant.model.interactors;

import android.support.annotation.NonNull;

import com.emedinaa.myrestaurant.data.callback.DataCallback;
import com.emedinaa.myrestaurant.data.remote.model.DishesResponse;
import com.emedinaa.myrestaurant.model.entity.Dish;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/10/18
 */
public class DishesRemoteInteractor extends BaseRemoteInteractor<DishesResponse> {

    protected DataCallback dataCallback;

    private Callback<DishesResponse> responseCallback= new Callback<DishesResponse>() {
        @Override
        public void onResponse(Call<DishesResponse> call, Response<DishesResponse> response) {
            if(response ==null){
                dataCallback.onFailure(new Exception(DEFAULT_MSG_ERROR));
            }
            if(response.isSuccessful() && response.body()!=null){
                DishesResponse dishesResponse=response.body();
                if(dishesResponse!=null && dishesResponse.getData()!=null){
                    List<Dish> dishes= dishesResponse.getData();
                    dataCallback.onSuccess(dishes);
                }else{
                    dataCallback.onFailure(new Exception(DEFAULT_MSG_ERROR));
                }
            }else{
                dataCallback.onFailure(new Exception(response.message()));
            }
        }

        @Override
        public void onFailure(Call<DishesResponse> call, Throwable t) {
            processFailure(call,t,dataCallback);
            /*if(call!=null){
                if(call.isCanceled()){ }else{
                    dataCallback.onFailure(new Exception(t));
                }
            }*/
        }
    };

    public void dishes(@NonNull final DataCallback mDataCallback){
        if(remote==null)return;
        dataCallback= mDataCallback;

        currentCall= remote.dishes();
        currentCall.enqueue(responseCallback);
    }

    public void dishesByCategory(@NonNull String categoryId,@NonNull final DataCallback mDataCallback){
        if(remote==null)return;
        dataCallback= mDataCallback;

        currentCall= remote.dishesByCategory(categoryId);
        currentCall.enqueue(responseCallback);
        /*currentCall.enqueue(new Callback<DishesResponse>() {
            @Override
            public void onResponse(Call<DishesResponse> call, Response<DishesResponse> response) {
                if(response ==null){
                    dataCallback.onFailure(new Exception(DEFAULT_MSG_ERROR));
                }
                if(response.isSuccessful() && response.body()!=null){
                    DishesResponse dishesResponse=response.body();
                    if(dishesResponse!=null && dishesResponse.getData()!=null){
                        List<Dish> dishes= dishesResponse.getData();
                        dataCallback.onSuccess(dishes);
                    }else{
                        dataCallback.onFailure(new Exception(DEFAULT_MSG_ERROR));
                    }
                }else{
                    dataCallback.onFailure(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<DishesResponse> call, Throwable t) {
                if(call!=null){
                    if(call.isCanceled()){ }else{
                        dataCallback.onFailure(new Exception(t));
                    }
                }
            }
        });*/
    }

    public void cancelOperation(){
        cancel();
    }
}
