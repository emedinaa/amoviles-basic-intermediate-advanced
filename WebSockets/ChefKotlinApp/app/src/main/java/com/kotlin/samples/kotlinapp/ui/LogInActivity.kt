package com.kotlin.samples.kotlinapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.emedinaa.core.ui.BasicActivity
import com.kotlin.samples.kotlinapp.data.DataInjector
import com.kotlin.samples.kotlinapp.data.local.PreferencesHelper
import com.kotlin.samples.kotlinapp.data.socket.SocketConstant
import com.kotlin.samples.kotlinapp.data.socket.SocketManager
import com.kotlin.samples.kotlinapp.data.socket.SocketMapper
import com.kotlin.samples.kotlinapp.model.User
import io.socket.client.Ack
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_log_in.*
import org.json.JSONException
import org.json.JSONObject
import com.kotlin.samples.kotlinapp.data.callback.DataCallback
import com.kotlin.samples.kotlinapp.model.interactors.UserRestInteractor


class LogInActivity : BasicActivity() {

    private lateinit var socketManager: SocketManager
    private lateinit var socket: Socket
    private var email: String? = null
    private var password: String? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kotlin.samples.kotlinapp.R.layout.activity_log_in)

        socket = DataInjector.getInstance().socketManager().socket()
        socketManager = DataInjector.getInstance().socketManager()
        ui()
    }

    private fun ui(){
        buttonLogIn.setOnClickListener {
            if(validate()){
                logIn()
            }
        }

        //mock data
        editTextEmail.setText("kelvin.ca91@gmail.com")
        editTextPassword.setText("123456")
    }

    private fun logIn(){
        val userRestInteractor = UserRestInteractor()
        userRestInteractor.logIn(email?:"", password?:"", object : DataCallback {
            override fun onSuccess(obj: Any) {
                user = obj as User
                startSocket()
            }

            override fun onFailure(exception: Exception) {
                //hideLoading();
                //logInLayout.setVisibility(View.VISIBLE);
                showMessage(exception.message?:"")
            }
        })
    }

    private fun validate(): Boolean {
        email = editTextEmail.text.toString().trim()
        password = editTextPassword.text.toString().trim()

        email?.let {
            return !it.isEmpty()
        }
        password?.let {
            return !it.isEmpty()
        }
        return true

    }

    private fun goToDashboard() {
        val bundle = Bundle()
        val intent = Intent(this, DashboardActivity::class.java)
        nextActivity(intent, bundle, true)
        //overridePendingTransition(0,0)
    }

    private val onConnect = Emitter.Listener { args ->
        Log.v("CONSOLE", "onConnect $args")
        socketLogIn()
    }

    private fun socketLogIn() {

        Log.v("CONSOLE", "socket " + socket.connected() + " user " + user)
        if (socket.connected() && user != null) {
            // Sending an object
            val obj = JSONObject()
            try {
                obj.put("user_id", user?.id)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            socketManager.emit(SocketConstant.EMIT_LOGIN, obj, Ack { args ->
                Log.v("CONSOLE", "EMIT LogIn $args")

                val data = args[0] as JSONObject
                val socketResponse = SocketMapper().convert(data)
                Log.v("CONSOLE", "EMIT LogIn $socketResponse")
                if (socketResponse.isSuccess) {
                    saveSession()

                    runOnUiThread {
                        hideLoading()
                        goToDashboard()
                    }
                } else {
                    runOnUiThread {
                        hideLoading()
                        showMessage("Ocurrió un error : " + socketResponse.message)
                    }
                }
            })
        }
    }

    private fun startSocket() {
        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.connect()
    }

    private fun saveSession() {
        PreferencesHelper.saveUser(this, user)
    }

    private fun showLoading() {}
    private fun hideLoading() {}
}
