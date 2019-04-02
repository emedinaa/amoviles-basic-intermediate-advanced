package com.kotlin.samples.kotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.kotlin.samples.kotlinapp.home.HomeFragment

class DashboardActivity : AppCompatActivity() ,DashboardListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        changeFragment(HomeFragment.newInstance("",""))
    }

    private fun changeFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,fragment,null)
            commit()
        }
    }

}
