package com.hhsj.ilive.ui.main.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.main.ConfirmFragment
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomEnableTextView


/**
 * 实名认证 Card 页面
 * @author YuHan
 */
class RealNameCardFragment : BaseFragment() {

    private lateinit var mRootView: ConstraintLayout
    private lateinit var mTopInfoRootView: ConstraintLayout
    private lateinit var mBottomRealNameRootView: ConstraintLayout
    private lateinit var mBackgroundImageView: ImageView
    private lateinit var mLogoImageView: ImageView

    //    private lateinit var mRealNameTipsTextView: TextView
    private lateinit var mRealNameTipsImageView: ImageView
    private lateinit var mRealNameBottomTipsTextView: TextView
    private lateinit var mStartAuthenticationTextView: CustomEnableTextView
    private lateinit var mRealNameMsgTextView: TextView
    private lateinit var mFlowerImageView: ImageView
    private lateinit var mRoundBackgroundImageView: ImageView

    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_real_name_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mTopInfoRootView = view.findViewById(R.id.top_info_root)
        mBottomRealNameRootView = view.findViewById(R.id.bottom_real_name_root)
        mBackgroundImageView = view.findViewById(R.id.iv_bg)
        mRealNameMsgTextView = view.findViewById(R.id.tv_real_name_msg)
        mFlowerImageView = view.findViewById(R.id.iv_flower)

        mLogoImageView = view.findViewById(R.id.iv_logo)
//        mRealNameTipsTextView = view.findViewById(R.id.tv_real_name_tips)
        mRealNameTipsImageView = view.findViewById(R.id.iv_real_name_tips)
        mRealNameBottomTipsTextView = view.findViewById(R.id.tv_real_name_bottom_tips)
        mStartAuthenticationTextView = view.findViewById(R.id.tv_start_authentication)
        mRoundBackgroundImageView = view.findViewById(R.id.iv_round_background)

        mStartAuthenticationTextView.canClick(true)
        initMargin()
        initSize()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mUserInfoViewModelProvider = (activity as BaseActivity).mUserInfoViewModelProvider
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }

    private fun initMargin() {
        margin(
            mRootView,
            mRoundBackgroundImageView,
            ConstraintSet.TOP,
            mRootView,
            ConstraintSet.TOP,
            30.8f
        )
        margin(
            mRootView,
            mRoundBackgroundImageView,
            ConstraintSet.START,
            mRootView,
            ConstraintSet.START,
            6.4f
        )
        margin(
            mRootView,
            mRoundBackgroundImageView,
            ConstraintSet.END,
            mRootView,
            ConstraintSet.END,
            6.4f
        )

        //top
        margin(
            mTopInfoRootView,
            mRealNameMsgTextView,
            ConstraintSet.TOP,
            mFlowerImageView,
            ConstraintSet.BOTTOM,
            3.1f
        )

        //real name authorization
        margin(
            mBottomRealNameRootView,
            mLogoImageView,
            ConstraintSet.TOP,
            mBottomRealNameRootView,
            ConstraintSet.TOP,
            10f
        )

        margin(
            mBottomRealNameRootView,
            mRealNameTipsImageView,
            ConstraintSet.TOP,
            mLogoImageView,
            ConstraintSet.BOTTOM,
            11f
        )
        margin(
            mBottomRealNameRootView,
            mStartAuthenticationTextView,
            ConstraintSet.TOP,
            mRealNameTipsImageView,
            ConstraintSet.BOTTOM,
            8f
        )
        margin(
            mBottomRealNameRootView,
            mRealNameBottomTipsTextView,
            ConstraintSet.TOP,
            mStartAuthenticationTextView,
            ConstraintSet.BOTTOM,
            8.2f
        )
    }

    private fun initSize() {
        calculateViewHeight(mRoundBackgroundImageView, 139.7f)
        calculateViewHeight(mTopInfoRootView, 51.3f)
    }

    private fun initListener() {
        //开始认证
        mStartAuthenticationTextView.setOnClickListener {
            val bundle = initConfirmFragmentBundle(
                title = resources.getString(R.string.real_name_authentication_title),
                picture = R.mipmap.icon_id_card,
                tips = resources.getString(R.string.real_name_authentication_explain),
                bottom1 = resources.getString(R.string.real_name_authentication_protocol),
                bottom2 = resources.getString(R.string.real_name_authentication_agree),
                confirmType = ConfirmFragment.Companion.ConfirmType.REAL_NAME_AUTHORIZATION
            )
            it.findNavController().navigate(
                R.id.action_identityAuthenticationFragment_to_identityAuthenticationConfirmFragment,
                bundle
            )
        }
    }
}