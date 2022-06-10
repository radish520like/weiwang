package com.hhsj.ilive.utils

import android.util.Log

private const val TAG = "LogUtils"

object LogUtils {

    fun e(msg: String?){
        Log.e(TAG, msg?:"null")
    }
}