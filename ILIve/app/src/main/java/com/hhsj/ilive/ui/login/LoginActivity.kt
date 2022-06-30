package com.hhsj.ilive.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.main.MainActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fromLogOutPage = intent.getBooleanExtra("fromLogOutPage",false)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        //判断是否有 token
        val phone = mUserInfoViewModelProvider.getPhone()
        val token = mUserInfoViewModelProvider.getToken()
        if(token.isNotEmpty() && !fromLogOutPage){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            if(fromLogOutPage || phone.isNotEmpty()){
                navController.setGraph(R.navigation.nav_logout_to_login)
            }else{
                navController.setGraph(R.navigation.nav_login_to_register)
            }
        }
    }
}