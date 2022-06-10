package com.hhsj.ilive.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.BaseFragment

/**
 * 登出后展示的登陆界面
 * @author YuHan
 */
class LoginForLogOutFragment : BaseFragment() {

    private lateinit var mLoginButton: TextView
    private lateinit var mPhoneTextView: TextView
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mSwitchAccountTextView: TextView
    private lateinit var mLineView: View
    private lateinit var mRootView: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_for_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mLoginButton = view.findViewById(R.id.btn_login)
        mPhoneTextView = view.findViewById(R.id.tv_phone)
        mLineView = view.findViewById(R.id.line_view)
        mAvatarImageView = view.findViewById(R.id.iv_avatar)
        mSwitchAccountTextView = view.findViewById(R.id.tv_switch_account)

        initMargin()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mActivity = activity as BaseActivity
        val mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider

        val header = mUserInfoViewModelProvider.getHeader()
        val phone = mUserInfoViewModelProvider.getPhone()

        Glide.with(requireContext()).load(header).into(mAvatarImageView)
        mPhoneTextView.text = phone.replaceRange(3..6,"****")
    }

    private fun initMargin(){
        margin(mRootView,mAvatarImageView, ConstraintSet.TOP,mRootView,ConstraintSet.TOP,40f)
        margin(mRootView,mLineView, ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,57.4f)
    }

    private fun initListener(){
        mLoginButton.setOnClickListener {
//            it.findNavController().popBackStack()
            it.findNavController().navigate(R.id.action_loginForLogOutFragment2_to_verifyCodeFragment)
        }
        mSwitchAccountTextView.setOnClickListener {
//            it.findNavController().popBackStack()
            it.findNavController().navigate(R.id.action_loginForLogOutFragment2_to_loginFragment2)
        }
    }
}