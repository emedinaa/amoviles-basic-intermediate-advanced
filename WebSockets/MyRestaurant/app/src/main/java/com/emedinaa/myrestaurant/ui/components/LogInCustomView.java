package com.emedinaa.myrestaurant.ui.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.emedinaa.core.media.PicassoImageLoader;
import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.data.DataInjector;
import com.emedinaa.myrestaurant.data.MediaPath;
import com.emedinaa.myrestaurant.data.callback.DataCallback;
import com.emedinaa.myrestaurant.data.local.Cart;
import com.emedinaa.myrestaurant.data.local.PreferencesHelper;
import com.emedinaa.myrestaurant.data.socket.SocketConstant;
import com.emedinaa.myrestaurant.data.socket.SocketManager;
import com.emedinaa.myrestaurant.data.socket.SocketMapper;
import com.emedinaa.myrestaurant.data.socket.SocketResponse;
import com.emedinaa.myrestaurant.model.entity.User;
import com.emedinaa.myrestaurant.model.interactors.UserRestInteractor;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * https://medium.com/square-corner-blog/android-leak-pattern-subscriptions-in-views-18f0860aa74c
 * @since : 8/4/18
 */
public class LogInCustomView extends LinearLayout {

    //private final String PATH="usuarios/";

    private ImageView imageUser;
    private EditText editTextEmail,editTextPassword;
    private View buttonLogIn,buttonLogOut;
    private View progressCircle,logInLayout;

    private String email, password;
    private Socket socket;
    private SocketManager socketManager;
    private User user=null;

    public LogInCustomView(Context context) {
        super(context);
        setUp(context);
    }

    public LogInCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUp(context);
    }

    public LogInCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context);
    }

    private void setUp(Context context){
        socket = DataInjector.getInstance().socketManager().socket();
        socketManager = DataInjector.getInstance().socketManager();

        //ui
        LayoutInflater inflater= LayoutInflater.from(context);
        logInLayout= inflater.inflate(R.layout.layout_login_view,null,false);
        logInLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        progressCircle= inflater.inflate(R.layout.layout_progress_bar,null,false);
        progressCircle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        this.addView(logInLayout);
        this.addView(progressCircle);


        imageUser= logInLayout.findViewById(R.id.imageUser);
        editTextEmail= logInLayout.findViewById(R.id.editTextEmail);
        editTextPassword= logInLayout.findViewById(R.id.editTextPassword);
        buttonLogIn= logInLayout.findViewById(R.id.buttonLogIn);
        buttonLogOut= logInLayout.findViewById(R.id.buttonLogOut);
        progressCircle.setVisibility(View.GONE);
        logInLayout.setVisibility(View.VISIBLE);
        //events
        buttonLogIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    logIn();
                }
            }
        });

        buttonLogOut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        checkSession();
    }

    private void checkSession() {
        user= PreferencesHelper.session(getContext());
        if(user!=null){
            Log.v("CONSOLE","user "+user.toString());
            if(!socketManager.isConnected()){
                showLoading();
                logInLayout.setVisibility(View.GONE);
                startSocket();
            }else{
                showViewSession(user);
            }
        }else{
            logOut();
        }
    }

    private boolean validate(){
        email= editTextEmail.getText().toString().trim();
        password= editTextPassword.getText().toString().trim();

        if(email.isEmpty()) return false;
        if(password.isEmpty()) return false;

        return true;
    }

    private void logOut(){

        imageUser.setVisibility(View.GONE);
        editTextPassword.setText("");
        editTextEmail.setText("");
        editTextPassword.setEnabled(true);
        editTextEmail.setEnabled(true);
        buttonLogIn.setVisibility(View.VISIBLE);
        buttonLogOut.setVisibility(View.GONE);

        //clear session
        PreferencesHelper.signOut(getContext());
        socketManager.clearSession();

        editTextEmail.setText("cliente3@gmail.com");
        editTextPassword.setText("123456");
    }

    private void logIn(){
        logInLayout.setVisibility(View.GONE);
        showLoading();
        UserRestInteractor userRestInteractor= new UserRestInteractor();
        userRestInteractor.logIn(email, password, new DataCallback() {
            @Override
            public void onSuccess(Object object) {
                user= (User)(object);
                startSocket();
            }

            @Override
            public void onFailure(Exception exception) {
                hideLoading();
                logInLayout.setVisibility(View.VISIBLE);
                showMessage(exception.getMessage());
            }
        });
    }

    private void showViewSession(User user){
        imageUser.setVisibility(View.VISIBLE);
        editTextEmail.setText(user.getEmail());
        editTextPassword.setText("");
        editTextPassword.setEnabled(false);
        editTextEmail.setEnabled(false);
        buttonLogIn.setVisibility(View.GONE);
        buttonLogOut.setVisibility(View.VISIBLE);
        //String userPath= BuildConfig.DOMAIN+BuildConfig.IMAGESPATH+PATH;
        PicassoImageLoader picassoImageLoader= new PicassoImageLoader();
        //picassoImageLoader.load(path+user.getImage(),imageUser);
        final String userPath=MediaPath.getInstance().clients()+user.getImage();
        picassoImageLoader.load(userPath,imageUser);

    }
    private void showMessage(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    private void startSocket(){
        socket.on(Socket.EVENT_CONNECT,onConnect);
        socket.connect();
    }

    private void socketLogIn(){
        //socketManager.connect(onLogIn);
        socket.on(SocketConstant.EMIT_LOGIN,onLogIn);

        Log.v("CONSOLE","socket "+socket.connected() +" user "+user );
       if(socket.connected() && user!=null){
           // Sending an object
           JSONObject obj = new JSONObject();
           try {
               obj.put("user_id", user.getId());
           } catch (JSONException e) {
               e.printStackTrace();
           }
           /*socketManager.emit(SocketConstant.EMIT_LOGIN, obj, new Ack() );
           socketManager.logIn(obj, new Ack() {
               @Override
               public void call(Object... args) {

               }
           });*/
           socket.emit(SocketConstant.EMIT_LOGIN, obj, new Ack() {
               @Override
               public void call(Object... args) {
                   Log.v("CONSOLE", "EMIT LogIn "+args);

                   JSONObject data = (JSONObject) args[0];
                   final SocketResponse socketResponse= new SocketMapper().convert(data);
                   Log.v("CONSOLE", "EMIT LogIn "+socketResponse.toString());
                   if(socketResponse.isSuccess()){
                        saveSession();
                        Cart.createOrder(user.getId());

                        post(new Runnable() {
                            @Override
                            public void run() {
                                hideLoading();
                                logInLayout.setVisibility(View.VISIBLE);
                                showViewSession(user);
                            }
                        });
                   }else{
                       post(new Runnable() {
                                @Override
                                public void run() {
                                    hideLoading();
                                    logInLayout.setVisibility(View.VISIBLE);
                                    showMessage("Ocurrió un error : "+socketResponse.getMessage());
                                }
                            });
                   }

                   /*boolean success;
                   String message;
                   try {
                       success = data.getBoolean("success");
                       message = data.getString("message");
                   } catch (JSONException e) {
                       Log.e("CONSOLE", e.getMessage());
                       return;
                   }

                   Log.v("CONSOLE", "EMIT LogIn -  success "+success+
                           " message "+message);*/
               }
           });
       }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(socketManager!=null){
            socketManager.removeEvent(SocketConstant.EMIT_LOGIN);
        }
    }

    private void saveSession(){
        PreferencesHelper.saveUser(getContext(),user);
    }

    private void showLoading(){
        progressCircle.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        progressCircle.setVisibility(View.GONE);
    }

    private Emitter.Listener onConnect= new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.v("CONSOLE", "onConnect "+args);
            socketLogIn();
        }
    };

    private Emitter.Listener onLogIn= new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.v("CONSOLE", "onLogIn "+args);
        }
    };

}
