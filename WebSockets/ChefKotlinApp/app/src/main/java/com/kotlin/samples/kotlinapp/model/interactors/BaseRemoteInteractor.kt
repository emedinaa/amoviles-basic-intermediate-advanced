package com.kotlin.samples.kotlinapp.model.interactors

import com.kotlin.samples.kotlinapp.data.DataInjector
import com.kotlin.samples.kotlinapp.data.callback.DataCallback
import com.kotlin.samples.kotlinapp.data.remote.KitchenEndPoint
import retrofit2.Call

open class BaseRemoteInteractor<T> {

    companion object {
        const val DEFAULT_MSG_ERROR:String="Ocurrió un error"
    }

    protected var currentCall:Call<T>?=null
    protected var remote: KitchenEndPoint = DataInjector.getInstance().remote().restaurantEndPoint()

    protected fun cancel() {
        currentCall?.let {
            it.cancel()
        }
    }

    protected fun processFailure(call: Call<T>?, t: Throwable,
                                 dataCallback: DataCallback) {
        if (call != null) {
            if (call.isCanceled) {
            } else {
                dataCallback.onFailure(Exception(t))
            }
        }
    }
}