package com.kotlin.samples.kotlinapp.model.interactors

import com.kotlin.samples.kotlinapp.data.remote.model.PlateResponse
import com.kotlin.samples.kotlinapp.data.callback.DataCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrdersRemoteInteractor:BaseRemoteInteractor<PlateResponse>() {

    fun orders(dataCallback: DataCallback) {
        if (remote == null) return

        currentCall = remote.orders()
        currentCall?.enqueue(object : Callback<PlateResponse> {
            override fun onResponse(call: Call<PlateResponse>, response: Response<PlateResponse>?) {
                if (response == null) {
                    dataCallback.onFailure(Exception(BaseRemoteInteractor.DEFAULT_MSG_ERROR))
                }

                response?.let {
                    if (it.isSuccessful && it.body() != null) {
                        val plateResponse = it.body()
                        if (plateResponse != null && plateResponse.data != null) {
                            val categories = plateResponse.data
                            dataCallback.onSuccess(categories)
                        } else {
                            dataCallback.onFailure(Exception(BaseRemoteInteractor.DEFAULT_MSG_ERROR))
                        }
                    } else {
                        dataCallback.onFailure(Exception(it.message()))
                    }
                }
            }

            override fun onFailure(call: Call<PlateResponse>, t: Throwable) {
                processFailure(call, t, dataCallback)
            }
        })
    }
}