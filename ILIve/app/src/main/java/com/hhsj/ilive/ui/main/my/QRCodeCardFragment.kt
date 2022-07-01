package com.hhsj.ilive.ui.main.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomFontTextView

/**
 * 二维码名片
 * @author YuHan
 */
class QRCodeCardFragment : BaseFragment() {

    private lateinit var mRootView: FrameLayout
    private lateinit var mRootCardView: CardView
    private lateinit var mTopInfoRootView: ConstraintLayout
    private lateinit var mBottomInfoRootView: ConstraintLayout
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mNickNameTextView: CustomFontTextView
    private lateinit var mPhoneTextView: CustomFontTextView
    private lateinit var mQrCodeImageView: ImageView
    private lateinit var mQrCodeTips: CustomFontTextView
    private lateinit var mRefreshImageView: ImageView

    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr_code_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mRootCardView = view.findViewById(R.id.card_view_root)
        mTopInfoRootView = view.findViewById(R.id.top_info_root)
        mBottomInfoRootView = view.findViewById(R.id.bottom_info_root)
        mAvatarImageView = view.findViewById(R.id.iv_avatar)
        mNickNameTextView = view.findViewById(R.id.tv_nick_name)
        mPhoneTextView = view.findViewById(R.id.tv_phone)
        mQrCodeImageView = view.findViewById(R.id.iv_qr_code)
        mQrCodeTips = view.findViewById(R.id.qr_code_tips)
        mRefreshImageView = view.findViewById(R.id.iv_refresh)

        initMargin()
        initSize()
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

        //TODO 圆角边框
        Glide.with(requireContext()).load(avatar).into(mAvatarImageView)
        mNickNameTextView.text = nickName
        mPhoneTextView.text = phone
    }

    private fun initMargin(){
        margin(mRootCardView,marginTop = 33.8f,marginStart = 6.4f,marginEnd = 6.4f)
        //AvatarCardView
        margin(mTopInfoRootView,mAvatarImageView,ConstraintSet.TOP,mTopInfoRootView,ConstraintSet.TOP,11.5f)
        margin(mTopInfoRootView,mAvatarImageView,ConstraintSet.START,mTopInfoRootView,ConstraintSet.START,18.2f)
        //nickName
        margin(mTopInfoRootView,mNickNameTextView,ConstraintSet.TOP,mAvatarImageView,ConstraintSet.BOTTOM,3.9f)
        margin(mTopInfoRootView,mNickNameTextView,ConstraintSet.START,mTopInfoRootView,ConstraintSet.START,18.2f)
        //phone
        margin(mTopInfoRootView,mPhoneTextView,ConstraintSet.TOP,mNickNameTextView,ConstraintSet.BOTTOM,1.2f)

        margin(mBottomInfoRootView,mQrCodeTips,ConstraintSet.TOP,mQrCodeImageView,ConstraintSet.BOTTOM,9.2f)
        margin(mBottomInfoRootView,mRefreshImageView,ConstraintSet.END,mBottomInfoRootView,ConstraintSet.END,4.9f)
        margin(mBottomInfoRootView,mRefreshImageView,ConstraintSet.BOTTOM,mBottomInfoRootView,ConstraintSet.BOTTOM,5.9f)
    }

    private fun initSize(){
        calculateViewHeight(mTopInfoRootView,51.3f)
        calculateViewHeight(mRootCardView,139.7f)

        calculateView(mQrCodeImageView,50.8f,50.8f)
    }
}