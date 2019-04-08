package com.emedinaa.myrestaurant.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.emedinaa.myrestaurant.BuildConfig;
import com.emedinaa.myrestaurant.data.remote.APIClient;
import com.emedinaa.myrestaurant.data.socket.SocketManager;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public class DataInjector {

    private static DataInjector instance;

    private APIClient remote;
    private @NonNull Context context;
    //private Socket mSocket;
    private SocketManager socketManager;

    private DataInjector(){}

    public static synchronized DataInjector getInstance(){
        if(instance == null){
            instance = new DataInjector();
        }
        return instance;
    }

    public void setUp(@NonNull Context mContext){
        context= mContext;
        remote=new APIClient(BuildConfig.DOMAIN);
        socketManager= new SocketManager();
        socketManager.init();
        /*try {
            mSocket = IO.socket(BuildConfig.DOMAIN);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }*/
    }

    public APIClient remote(){
        return this.remote;
    }

    /*public Socket socket(){
        return mSocket;
    }*/

    public SocketManager socketManager() {
        return socketManager;
    }
}