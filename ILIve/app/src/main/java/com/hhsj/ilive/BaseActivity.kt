package com.hhsj.ilive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.hhsj.ilive.viewmodels.UserInfoViewModel

open class BaseActivity : AppCompatActivity() {

    lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserInfoViewModelProvider =
            ViewModelProvider.NewInstanceFactory().create(UserInfoViewModel::class.java)
    }
}