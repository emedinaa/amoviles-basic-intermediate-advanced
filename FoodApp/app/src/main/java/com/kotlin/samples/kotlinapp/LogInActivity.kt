package com.kotlin.samples.kotlinapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        buttonLogIn.setOnClickListener {
            goToDashboard()
        }
    }

    private fun goToDashboard(){
        startActivity(Intent(this,DashboardActivity::class.java))
    }
}
