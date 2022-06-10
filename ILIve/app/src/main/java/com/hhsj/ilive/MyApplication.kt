package com.hhsj.ilive

import android.app.Application
import com.hhsj.ilive.utils.SPUtils

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        SPUtils.init(this)
    }
}