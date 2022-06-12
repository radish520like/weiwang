package com.hhsj.ilive.ui.main.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.BaseFragment
import com.hhsj.ilive.utils.LogUtils
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomEnableTextView

/**
 * 用户信息
 * @author YuHan
 */
class SettingFragment : BaseFragment() {

    private lateinit var mLogoutTextView: CustomEnableTextView
    private lateinit var mBackImageView: ImageView
    private lateinit var mActivity: UserInfoActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLogoutTextView = view.findViewById(R.id.tv_logout)
        mBackImageView = view.findViewById(R.id.iv_back)

        mLogoutTextView.canClick(true)
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as UserInfoActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider
    }

    private fun initListener() {
        mBackImageView.setOnClickListener {
            mActivity.finish()
        }

        mLogoutTextView.setOnClickListener {
            it.findNavController().navigate(R.id.action_userInfoFragment_to_logOutFragment)
        }
    }
}