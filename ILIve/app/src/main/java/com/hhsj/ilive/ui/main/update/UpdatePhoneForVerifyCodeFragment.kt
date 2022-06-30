package com.hhsj.ilive.ui.main.update

import android.content.Intent
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
import com.hhsj.ilive.*
import com.hhsj.ilive.widget.CustomEnableTextView

/**
 * 修改手机号--获取验证码
 * @author YuHan
 */
class UpdatePhoneForVerifyCodeFragment : BaseFragment() {

    private lateinit var mLoginButton: CustomEnableTextView
    private lateinit var mPhoneTextView: TextView
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mHelperImageView: ImageView
    private lateinit var mBackImageView: ImageView
    private lateinit var mLineView: View
    private lateinit var mRootView: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_phone_for_verifycode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mLoginButton = view.findViewById(R.id.btn_login)
        mPhoneTextView = view.findViewById(R.id.tv_phone)
        mLineView = view.findViewById(R.id.line_view)
        mAvatarImageView = view.findViewById(R.id.iv_avatar)
        mHelperImageView = view.findViewById(R.id.iv_help)
        mBackImageView = view.findViewById(R.id.iv_back)

        mLoginButton.canClick(true)
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
        mPhoneTextView.text = phone
    }

    private fun initMargin(){
        margin(mRootView,mAvatarImageView, ConstraintSet.TOP,mRootView,ConstraintSet.TOP,40f)
        margin(mRootView,mLineView, ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,44.1f)
    }

    private fun initListener(){
        mLoginButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(VERIFY_CODE_FROM_KEY,VERIFY_CODE_FROM_UPDATE_PHONE_OLD)
            it.findNavController().navigate(R.id.action_UpdatePhoneForVerifyCodeFragment_to_verifyCodeFragment,bundle)
        }
        mHelperImageView.setOnClickListener {
            val intent = Intent(activity,AccountSecurityActivity::class.java)
            startActivity(intent)
        }
        mBackImageView.setOnClickListener {
            activity?.finish()
        }
    }
}