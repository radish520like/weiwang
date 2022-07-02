package com.hhsj.ilive.ui.main

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
import com.hhsj.ilive.R
import com.hhsj.ilive.viewmodels.UserInfoViewModel

/**
 * 确定界面
 * @author YuHan
 */
class ConfirmFragment : BaseFragment() {

    companion object{
        @JvmStatic
        val TITLE = "TITLE"
        val ICON = "ICON"
        val TIPS = "tips"
        val BOTTOM_ITEM1 = "bottom_item1"
        val BOTTOM_ITEM2 = "bottom_item2"
        val TYPE = "type"

        enum class ConfirmType{
            UPDATE_PHONE,
            UPDATE_AVATAR,
            REAL_NAME_AUTHORIZATION
        }
    }

    private lateinit var mTitleTextView: TextView
    private lateinit var mLogoImageView: ImageView
    private lateinit var mExplainTextView: TextView
    private lateinit var mBottomView: View
    private lateinit var mBottomLinearLayout: LinearLayout
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mConfirmTextView: TextView
    private lateinit var mTipsTextView: TextView
    private lateinit var mCancelTextView: TextView
    private var mFromType: ConfirmType? = null
    private lateinit var mActivity: BaseActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mBottomLinearLayout = view.findViewById(R.id.bottom_ll)
        mTitleTextView = view.findViewById(R.id.tv_title)
        mLogoImageView = view.findViewById(R.id.iv_logo)
        mBottomView = view.findViewById(R.id.bottom_view)
        mExplainTextView = view.findViewById(R.id.tv_explain)

        mTipsTextView = view.findViewById(R.id.tv_tips)
        mConfirmTextView = view.findViewById(R.id.tv_confirm)
        mCancelTextView = view.findViewById(R.id.tv_cancel)

        initViewMargin()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as BaseActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider

        val title = arguments?.getString(TITLE) ?: resources.getString(R.string.update_user_info_phone_confirm_title)
        val icon = arguments?.getInt(ICON) ?: R.mipmap.icon_logout
        val explain = arguments?.getString(TIPS) ?: resources.getString(R.string.update_user_info_phone_confirm_tips)
        val bottomItem1 = arguments?.getString(BOTTOM_ITEM1) ?: mUserInfoViewModelProvider.getPhone()
        val bottomItem2 = arguments?.getString(BOTTOM_ITEM2) ?: resources.getString(R.string.update_user_info_phone_confirm)
        mFromType = arguments?.get(TYPE) as ConfirmType?

        mTitleTextView.text = title
        mLogoImageView.setImageResource(icon)
        mExplainTextView.text = explain
        mTipsTextView.text = bottomItem1
        mConfirmTextView.text = bottomItem2

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
        mConfirmTextView.setOnClickListener {
//            mUserInfoViewModelProvider.logout({
//                LogUtils.e("logout Success")
//            }, {})
//            val intent = Intent(activity, LoginActivity::class.java)
//            intent.putExtra("fromLogOutPage",true)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//            activity?.finish()
        }
        mCancelTextView.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}