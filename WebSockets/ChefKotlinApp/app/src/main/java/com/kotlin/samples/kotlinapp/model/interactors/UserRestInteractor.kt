package com.kotlin.samples.kotlinapp.model.interactors

import com.kotlin.samples.kotlinapp.data.remote.model.LogInResponse
import com.kotlin.samples.kotlinapp.data.remote.model.LogInRaw
import com.kotlin.samples.kotlinapp.data.callback.DataCallback


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class UserRestInteractor:BaseRemoteInteractor<LogInResponse>() {

    fun logIn(email: String, password: String,
              dataCallback: DataCallback) {
        if (remote == null) return

        currentCall = remote.logIn(LogInRaw(email, password))

        currentCall?.enqueue(object : Callback<LogInResponse> {

            override fun onResponse(call: Call<LogInResponse>, response: Response<LogInResponse>?) {
                if (response == null) {
                    dataCallback.onFailure(Exception(BaseRemoteInteractor.DEFAULT_MSG_ERROR))
                }
                response?.let {
                    if (it.isSuccessful && it.body() != null) {
                        val logInResponse = it.body()
                        if (logInResponse != null && logInResponse.data != null) {
                            val user = logInResponse.data
                            dataCallback.onSuccess(user)
                        } else {
                        }
                    } else {
                        dataCallback.onFailure(Exception(it.message()))
                    }
                }
            }

            override fun onFailure(call: Call<LogInResponse>, t: Throwable) {
                processFailure(call, t, dataCallback)
            }
        })
    }
}