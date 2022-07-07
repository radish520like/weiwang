package com.hhsj.ilive.ui.main.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
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

    private lateinit var mRootView: FrameLayout
    private lateinit var mRootCardView: CardView
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
        mRootCardView = view.findViewById(R.id.card_view_root)
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

        val avatar = mUserInfoViewModelProvider.getHeader()
        val nickName = mUserInfoViewModelProvider.getNickName()
        val phone = mUserInfoViewModelProvider.getPhone()

        val realWidth = getRealWidth(50.8f)
        val realHeight = getRealHeight(50.8f)
    }

    private fun initMargin() {
        margin(mRootCardView, marginTop = 33.8f, marginStart = 6.4f, marginEnd = 6.4f)
        //top
        margin(mTopInfoRootView,mRealNameMsgTextView,ConstraintSet.TOP,mFlowerImageView,ConstraintSet.BOTTOM,3.1f)

        //real name authorization
        margin(
            mBottomRealNameRootView,
            mLogoImageView,
            ConstraintSet.TOP,
            mBottomRealNameRootView,
            ConstraintSet.TOP,
            10.3f
        )

        margin(
            mBottomRealNameRootView,
            mRealNameTipsImageView,
            ConstraintSet.TOP,
            mLogoImageView,
            ConstraintSet.BOTTOM,
            11.5f
        )
        margin(
            mBottomRealNameRootView,
            mStartAuthenticationTextView,
            ConstraintSet.TOP,
            mRealNameTipsImageView,
            ConstraintSet.BOTTOM,
            8.21f
        )
        margin(
            mBottomRealNameRootView,
            mRealNameBottomTipsTextView,
            ConstraintSet.TOP,
            mStartAuthenticationTextView,
            ConstraintSet.BOTTOM,
            8.21f
        )
    }

    private fun initSize() {
        calculateViewHeight(mTopInfoRootView, 51.3f)
        calculateViewHeight(mRootCardView, 139.7f)
    }

    private fun initListener(){
        //开始认证
        mStartAuthenticationTextView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(ConfirmFragment.TITLE,resources.getString(R.string.real_name_authentication_title))
            bundle.putInt(ConfirmFragment.ICON,R.mipmap.icon_id_card)
            bundle.putString(ConfirmFragment.TIPS,resources.getString(R.string.real_name_authentication_explain))
            bundle.putString(ConfirmFragment.BOTTOM_ITEM1,getString(R.string.real_name_authentication_protocol))
            bundle.putString(ConfirmFragment.BOTTOM_ITEM2,getString(R.string.real_name_authentication_agree))
            bundle.putSerializable(ConfirmFragment.TYPE,
                ConfirmFragment.Companion.ConfirmType.REAL_NAME_AUTHORIZATION
            )
            it.findNavController().navigate(R.id.action_identityAuthenticationFragment_to_identityAuthenticationConfirmFragment,bundle)
        }
    }
}