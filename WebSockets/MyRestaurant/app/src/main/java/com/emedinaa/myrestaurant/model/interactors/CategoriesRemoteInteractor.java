package com.emedinaa.myrestaurant.model.interactors;

import android.support.annotation.NonNull;

import com.emedinaa.myrestaurant.data.callback.DataCallback;
import com.emedinaa.myrestaurant.data.remote.model.CategoriesResponse;
import com.emedinaa.myrestaurant.model.entity.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * https://square.github.io/retrofit/
 * @since : 8/10/18
 */
public class CategoriesRemoteInteractor extends BaseRemoteInteractor<CategoriesResponse> {

    public void categoriesWT(@NonNull String token,@NonNull final DataCallback dataCallback){
        if(remote==null)return;

        currentCall= remote.categoriesWT(token);
        currentCall.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if(response ==null){
                    dataCallback.onFailure(new Exception(DEFAULT_MSG_ERROR));
                }
                if(response.isSuccessful() && response.body()!=null){
                    CategoriesResponse categoryResponse=response.body();
                    if(categoryResponse!=null && categoryResponse.getData()!=null){
                        List<Category> categories= categoryResponse.getData();
                        dataCallback.onSuccess(categories);
                    }else{
                        dataCallback.onFailure(new Exception(DEFAULT_MSG_ERROR));
                    }
                }else{
                    dataCallback.onFailure(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                processFailure(call,t,dataCallback);
                /*if(call!=null){
                    if(call.isCanceled()){ }else{
                        dataCallback.onFailure(new Exception(t));
                    }
                }*/
            }
        });
    }
    public void categories(@NonNull final DataCallback dataCallback){
        if(remote==null)return;

        currentCall= remote.categories();
        currentCall.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if(response ==null){
                    dataCallback.onFailure(new Exception(DEFAULT_MSG_ERROR));
                }
                if(response.isSuccessful() && response.body()!=null){
                    CategoriesResponse categoryResponse=response.body();
                    if(categoryResponse!=null && categoryResponse.getData()!=null){
                        List<Category> categories= categoryResponse.getData();
                        dataCallback.onSuccess(categories);
                    }else{
                        dataCallback.onFailure(new Exception(DEFAULT_MSG_ERROR));
                    }
                }else{
                    dataCallback.onFailure(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                processFailure(call,t,dataCallback);
                /*if(call!=null){
                    if(call.isCanceled()){ }else{
                        dataCallback.onFailure(new Exception(t));
                    }
                }*/
            }
        });
    }

    public void cancelOperation(){
        cancel();
    }
}
