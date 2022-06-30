package com.hhsj.ilive.ui.main.my

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.R

class UserInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val forUpdate = intent.getBooleanExtra("forUpdate",false)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        if(forUpdate){
            navController.setGraph(R.navigation.nav_logout_login_forupdate)
        }else{
            navController.setGraph(R.navigation.nav_logout_login)
        }
    }
}