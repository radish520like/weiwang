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
import com.hhsj.ilive.ui.main.update.UpdatePhoneActivity
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomMyInfoItemView

/**
 * 账户安全
 * @author YuHan
 */
class SettingAccountSecurityFragment : BaseFragment() {

    private lateinit var mBackImageView: ImageView
    private lateinit var mAccountSafeInfoItemView: CustomMyInfoItemView

    private lateinit var mActivity: UserInfoActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_account_security, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBackImageView = view.findViewById(R.id.iv_back)
        mAccountSafeInfoItemView = view.findViewById(R.id.info_item_view_account_safe)

        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as UserInfoActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider

        val phone = mUserInfoViewModelProvider.getPhone()

        mAccountSafeInfoItemView.setValue(phone)
    }

    private fun initListener() {
        mBackImageView.setOnClickListener {
            goBack(it.findNavController())
        }

        mAccountSafeInfoItemView.setOnClickListener {
            val intent = Intent(requireContext(),UpdatePhoneActivity::class.java)
            startActivity(intent)
        }

//        mLogoutTextView.setOnClickListener {
//            it.findNavController().navigate(R.id.action_userInfoFragment_to_logOutFragment)
//        }
    }
}