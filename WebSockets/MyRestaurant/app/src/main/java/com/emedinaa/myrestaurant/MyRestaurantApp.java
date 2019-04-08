package com.emedinaa.myrestaurant;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.emedinaa.myrestaurant.data.DataInjector;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public class MyRestaurantApp extends Application {

    private final DataInjector dataInjector= DataInjector.getInstance();
    private final String imagesPath= BuildConfig.DOMAIN+BuildConfig.IMAGESPATH;
    //private Socket mSocket;

    @Override
    public void onCreate() {
        super.onCreate();
        dataInjector.setUp(this);
        /*try {
            mSocket = IO.socket(BuildConfig.DOMAIN);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }*/
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public String getImagesPath() {
        return imagesPath;
    }

    /*public Socket getSocket(){
        return mSocket;
    }*/
}
