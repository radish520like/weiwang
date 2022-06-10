package com.hhsj.ilive.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.hhsj.ilive.R

class LoginRegisterBottomView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val mRootView =
        LayoutInflater.from(context).inflate(R.layout.login_regist_bottom_layout, this, true)

    private val mLoginRegisterTextView: TextView = mRootView.findViewById(R.id.tv_login_register)
    private val mUrgentFreezeTextView: TextView = mRootView.findViewById(R.id.tv_login_register)
    private val mMoreOptionsTextView: TextView = mRootView.findViewById(R.id.tv_more_options)

    init{
        mLoginRegisterTextView.setOnClickListener {
            Toast.makeText(context, "找回密码", Toast.LENGTH_SHORT).show()
        }

        mUrgentFreezeTextView.setOnClickListener {
            Toast.makeText(context, "紧急冻结", Toast.LENGTH_SHORT).show()
        }

        mMoreOptionsTextView.setOnClickListener {
            Toast.makeText(context, "更多选项", Toast.LENGTH_SHORT).show()
        }
    }
}