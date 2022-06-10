package com.hhsj.ilive.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.BaseFragment
import com.hhsj.ilive.widget.CustomCountDownView

/**
 * GestureLoginFragment
 * @author YuHan
 */
class GestureLoginFragment : BaseFragment() {

    private lateinit var mTelephoneEditText: EditText
    private lateinit var mVerifyCodeEditText: EditText
    private lateinit var mLoginButton: Button
    private lateinit var mCountDownInputVerifyCode: CustomCountDownView
    private var mTelephonePass: Boolean = false
    private var mVerifyCodePass: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTelephoneEditText = view.findViewById(R.id.et_telephone)
        mVerifyCodeEditText = view.findViewById(R.id.et_verify_code)
        mLoginButton = view.findViewById(R.id.btn_login)
        mCountDownInputVerifyCode = view.findViewById(R.id.count_down_input_verify_code)

        initListener()
    }

    private fun initListener(){
        mTelephoneEditText.addTextChangedListener{
            mTelephonePass = it?.length!! > 0
            enableLogin()
        }
        mVerifyCodeEditText.addTextChangedListener {
            mVerifyCodePass = it?.length!! == 6
            enableLogin()
        }
        mLoginButton.setOnClickListener {
            Toast.makeText(context, "登录", Toast.LENGTH_SHORT).show()
        }
        mCountDownInputVerifyCode.setOnClickListener {
            mCountDownInputVerifyCode.startCountDown(5000,100)
        }
    }

    private fun enableLogin(){
        mLoginButton.isEnabled = mTelephonePass && mVerifyCodePass
    }
}