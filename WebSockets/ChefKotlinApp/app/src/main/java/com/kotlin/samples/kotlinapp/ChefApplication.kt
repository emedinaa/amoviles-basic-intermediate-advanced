package com.kotlin.samples.kotlinapp

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.kotlin.samples.kotlinapp.data.DataInjector



class ChefApplication:Application() {

    private val dataInjector = DataInjector.getInstance()
    private val imagesPath = BuildConfig.DOMAIN + BuildConfig.IMAGESPATH

    override fun onCreate() {
        super.onCreate()
        dataInjector.setUp(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun getImagesPath(): String {
        return imagesPath
    }
}