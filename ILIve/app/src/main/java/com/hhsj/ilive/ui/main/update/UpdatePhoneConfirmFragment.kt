package com.hhsj.ilive.ui.main.update

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.ui.login.LoginActivity
import com.hhsj.ilive.R
import com.hhsj.ilive.utils.LogUtils
import com.hhsj.ilive.viewmodels.UserInfoViewModel

/**
 * 更换绑定手机号确定界面
 * @author YuHan
 */
class UpdatePhoneConfirmFragment : BaseFragment() {

    private lateinit var mTitleTextView: TextView
    private lateinit var mLogoImageView: ImageView
    private lateinit var mExplainTextView: TextView
    private lateinit var mBottomView: View
    private lateinit var mBottomLinearLayout: LinearLayout
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mLogoutTextView: TextView
    private lateinit var mCancelTextView: TextView
    private lateinit var mActivity: BaseActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_phone_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mBottomLinearLayout = view.findViewById(R.id.bottom_ll)
        mTitleTextView = view.findViewById(R.id.tv_title)
        mLogoImageView = view.findViewById(R.id.iv_logo)
        mBottomView = view.findViewById(R.id.bottom_view)
        mExplainTextView = view.findViewById(R.id.tv_explain)

        mLogoutTextView = view.findViewById(R.id.tv_logout)
        mCancelTextView = view.findViewById(R.id.tv_cancel)

        initViewMargin()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as BaseActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider
    }

    private fun initViewMargin() {
        margin(mRootView,mTitleTextView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,39.2f)
        margin(mRootView,mLogoImageView,ConstraintSet.TOP,mTitleTextView,ConstraintSet.BOTTOM,15.2f)
        margin(mRootView,mExplainTextView,ConstraintSet.TOP,mLogoImageView,ConstraintSet.BOTTOM,10.2f)
    }

    private fun initListener() {
        mRootView.setOnClickListener {
            hideSoft()
        }
        mLogoutTextView.setOnClickListener {
            mUserInfoViewModelProvider.logout({
                LogUtils.e("logout Success")
            }, {})
            val intent = Intent(activity, LoginActivity::class.java)
            intent.putExtra("fromLogOutPage",true)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            activity?.finish()
        }
        mCancelTextView.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}