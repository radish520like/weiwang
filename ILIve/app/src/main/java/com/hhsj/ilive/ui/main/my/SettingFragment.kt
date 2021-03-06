package com.hhsj.ilive.ui.main.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.login.LoginActivity
import com.hhsj.ilive.ui.main.ConfirmFragment
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomEnableTextView
import com.hhsj.ilive.widget.CustomMyInfoItemView

/**
 * 用户信息
 * @author YuHan
 */
class SettingFragment : BaseFragment() {

    private lateinit var mLogoutTextView: CustomEnableTextView
    private lateinit var mSwitchAccount: CustomEnableTextView
    private lateinit var mBackImageView: ImageView
    private lateinit var mAccountSafeInfoItemView: CustomMyInfoItemView

    private lateinit var mActivity: UserInfoActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLogoutTextView = view.findViewById(R.id.tv_logout)
        mSwitchAccount = view.findViewById(R.id.tv_switch_account)
        mBackImageView = view.findViewById(R.id.iv_back)
        mAccountSafeInfoItemView = view.findViewById(R.id.info_item_view_account_safe)

        mLogoutTextView.canClick(true)
        mSwitchAccount.canClick(true)
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

        mSwitchAccount.setOnClickListener {
            mUserInfoViewModelProvider.logout(success = {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            },failure = {
            })
        }

        mAccountSafeInfoItemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingFragment_to_settingAccountSecurityFragment)
        }

        mLogoutTextView.setOnClickListener {
            val bundle = initConfirmFragmentBundle(
                title = resources.getString(R.string.logout_title),
                picture = R.mipmap.icon_logout,
                tips = resources.getString(R.string.user_info_logout_dialog_title),
                bottom1 = resources.getString(R.string.user_info_logout_dialog_title),
                bottom2 = resources.getString(R.string.user_info_logout_dialog_exit),
                confirmType = ConfirmFragment.Companion.ConfirmType.LOG_OUT
            )
            it.findNavController().navigate(R.id.action_userInfoFragment_to_logOutFragment,bundle)
        }
    }
}