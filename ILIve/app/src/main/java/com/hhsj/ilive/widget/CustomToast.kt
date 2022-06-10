package com.hhsj.ilive.widget

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Vibrator
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.hhsj.ilive.R

class CustomToast private constructor(){

    companion object{
        @Volatile private var INSTANCE:CustomToast? = null
        private lateinit var mToast: Toast
        private lateinit var mVibrator: Vibrator

        fun getInstance(context: Context): CustomToast = INSTANCE ?: synchronized(this){
            INSTANCE?: CustomToast().also {
                val view = LayoutInflater.from(context.applicationContext).inflate(R.layout.layout_custom_toast,null)
                mToast = Toast(context.applicationContext)
                mToast.setGravity(Gravity.CENTER,0,0)
                mToast.duration = Toast.LENGTH_SHORT
                mToast.view = view
                mVibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
                INSTANCE = it
            }
        }
    }

    fun show(msg: String){
        val view = mToast.view
        view?.findViewById<TextView>(R.id.tv_msg)?.text = msg
        mToast.show()
        mVibrator.vibrate(600)
    }

    fun hide(){
        mToast.cancel()
        mVibrator.cancel()
    }
}