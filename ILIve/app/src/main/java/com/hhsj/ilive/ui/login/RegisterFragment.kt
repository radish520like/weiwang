package com.hhsj.ilive.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.BaseFragment
import com.hhsj.ilive.widget.CustomCheckBox

/**
 * 注册界面
 * @author YuHan
 */
class RegisterFragment : BaseFragment() {

    private lateinit var mTelephoneEditText: EditText
    private lateinit var mVerifyCodeEditText: EditText
    private lateinit var mRegisterButton: Button
    private lateinit var mHasReadCheckBox: CustomCheckBox
    private lateinit var mProtocolLinkTextView: TextView
    private lateinit var mNavController: NavController
    private lateinit var mLoginRegisterTextView: TextView
    private var mTelephonePass: Boolean = false
    private var mPassWordPass: Boolean = false
    private var mHasReadChecked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        mTelephoneEditText = view.findViewById(R.id.et_telephone)
        mVerifyCodeEditText = view.findViewById(R.id.et_verify_code)
        mRegisterButton = view.findViewById(R.id.btn_register)
        mHasReadCheckBox = view.findViewById(R.id.checkbox_read)
        mProtocolLinkTextView = view.findViewById(R.id.tv_protocol_link)
        mLoginRegisterTextView = view.findViewById(R.id.tv_login_register)

        mLoginRegisterTextView.text = resources.getString(R.string.return_to_login)

        initListener()
    }

    private fun initListener(){
        mTelephoneEditText.addTextChangedListener{
            mTelephonePass = it?.length!! == 11
            enableAgreeAndRegister()
        }
        mVerifyCodeEditText.addTextChangedListener {
            mPassWordPass = it?.length!! == 6
            enableAgreeAndRegister()
        }
        mRegisterButton.setOnClickListener {
            Toast.makeText(context, "注册", Toast.LENGTH_SHORT).show()
        }
        mHasReadCheckBox.onCheckedChangeListener {
            mHasReadChecked = it
            enableAgreeAndRegister()
        }
        mLoginRegisterTextView.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun enableAgreeAndRegister(){
        mRegisterButton.isEnabled = mTelephonePass && mPassWordPass && mHasReadChecked
    }
}