package com.hhsj.ilive.ui.main.avatar

import android.os.Bundle
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.R

class UpdateAvatarActivity : BaseActivity() {

    companion object{
        @JvmStatic
        val PREVIEW_AVATAR = "preview_avatar"
        @JvmStatic
        val COMPRESS_PATH = "compress_path"
        @JvmStatic
        val ORIGINAL_PATH = "original_path"
    }

    var mCompressPath: String = ""
    var mOriginalPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_avatar)

        val isPreviewAvatar = intent.getBooleanExtra(PREVIEW_AVATAR,false)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        if(isPreviewAvatar){
            //预览头像
            navController.setGraph(R.navigation.nav_preview_avatar)
        }else{
            //修改头像
            navController.setGraph(R.navigation.nav_update_avatar)
            mCompressPath = intent.getStringExtra(COMPRESS_PATH) ?: ""
            mOriginalPath = intent.getStringExtra(ORIGINAL_PATH) ?: ""
        }
    }
}