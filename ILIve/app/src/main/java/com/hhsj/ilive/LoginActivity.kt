package com.hhsj.ilive

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val fromLogOutPage = intent.getBooleanExtra("fromLogOutPage",false)
        if(fromLogOutPage){
            navController.setGraph(R.navigation.nav_logout_to_login)
        }else{
            navController.setGraph(R.navigation.nav_login_to_register)
        }

    }
}