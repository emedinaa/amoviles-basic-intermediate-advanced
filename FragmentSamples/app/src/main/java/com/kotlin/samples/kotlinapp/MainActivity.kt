package com.kotlin.samples.kotlinapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import com.kotlin.samples.kotlinapp.extensions.replaceJava
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val onClickListener= View.OnClickListener {
        var mActivity:Class<*>?=null
        when(it.id){
            R.id.button1 -> mActivity=FS1Activity::class.java
            R.id.button2 -> mActivity=FS2Activity::class.java
            R.id.button3 -> mActivity=FS3Activity::class.java
            R.id.button4 -> mActivity=FS4Activity::class.java
            R.id.button5 -> mActivity=FCommunicationActivity::class.java
            R.id.button6 -> mActivity=FExerciseActivity::class.java
        }
        mActivity?.let {
            goToView(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener(onClickListener)
        button2.setOnClickListener(onClickListener)
        button3.setOnClickListener(onClickListener)
        button4.setOnClickListener(onClickListener)
        button5.setOnClickListener(onClickListener)
        button6.setOnClickListener(onClickListener)
    }

    private fun goToView(mActivity:Class<*>){
        startActivity(Intent(this,mActivity))
    }

}
