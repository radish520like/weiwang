package com.hhsj.ilive.ui.main.my

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.main.ConfirmFragment
import com.hhsj.ilive.utils.QRCodeUtils
import com.hhsj.ilive.utils.TimeUtils
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomEnableTextView
import com.hhsj.ilive.widget.CustomFontTextView
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * 二维码名片
 * @author YuHan
 */
class QRCodeCardFragment : BaseFragment() {

    private lateinit var mRootView: ConstraintLayout
    private lateinit var mTopInfoRootView: ConstraintLayout
    private lateinit var mBottomQrCodeRootView: ConstraintLayout
    private lateinit var mBottomRealNameRootView: ConstraintLayout
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mCardViewAvatarImageView: CardView
    private lateinit var mBackgroundImageView: ImageView
    private lateinit var mNickNameTextView: CustomFontTextView
    private lateinit var mPhoneTextView: CustomFontTextView
    private lateinit var mQrCodeImageView: ImageView
    private lateinit var mQrCodeTips: CustomFontTextView
    private lateinit var mRefreshImageView: ImageView
    private lateinit var mLogoImageView: ImageView

    //    private lateinit var mRealNameTipsTextView: TextView
    private lateinit var mRealNameTipsImageView: ImageView
    private lateinit var mRealNameBottomTipsTextView: TextView
    private lateinit var mStartAuthenticationTextView: CustomEnableTextView
    private lateinit var mRoundBackgroundImageView: ImageView

    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel
    private lateinit var mOutAnimatorSet: AnimatorSet
    private lateinit var mInAnimatorSet: AnimatorSet

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
        mTopInfoRootView = view.findViewById(R.id.top_info_root)
        mBottomQrCodeRootView = view.findViewById(R.id.bottom_qr_code_root)
        mBottomRealNameRootView = view.findViewById(R.id.bottom_real_name_root)
        mAvatarImageView = view.findViewById(R.id.iv_avatar)
        mCardViewAvatarImageView = view.findViewById(R.id.cardview_avatar)
        mBackgroundImageView = view.findViewById(R.id.iv_bg)
        mNickNameTextView = view.findViewById(R.id.tv_nick_name)
        mPhoneTextView = view.findViewById(R.id.tv_phone)
        mQrCodeImageView = view.findViewById(R.id.iv_qr_code)
        mQrCodeTips = view.findViewById(R.id.qr_code_tips)
        mRefreshImageView = view.findViewById(R.id.iv_refresh)

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
        initAnimator()
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

        Glide.with(requireContext()).load(avatar).apply(
            RequestOptions.bitmapTransform(
                RoundedCornersTransformation(7, 1, RoundedCornersTransformation.CornerType.ALL)
            )
        ).into(mAvatarImageView)

        Glide.with(requireContext()).load(avatar).transform(BlurTransformation(25)).apply(
            RequestOptions.bitmapTransform(
                RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.TOP)
            )
        ).into(mBackgroundImageView)
        mNickNameTextView.text = nickName
        mPhoneTextView.text = phone

        val realWidth = getRealWidth(50.7f)
        val realHeight = getRealHeight(50.7f)


        val stringBuilder = StringBuilder()
        stringBuilder.append(phone).append("\n").append(TimeUtils.getCurrentDataFormatTime())
        val qrBitmap = QRCodeUtils.createQRCodeBitmap(
            stringBuilder.toString(),
            realWidth,
            realHeight,
            "UTF-8",
            "L",
            "1",
            Color.BLACK,
            Color.WHITE
        )
        mQrCodeImageView.setImageBitmap(qrBitmap)
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

        //AvatarCardView
        margin(
            mTopInfoRootView,
            mCardViewAvatarImageView,
            ConstraintSet.TOP,
            mTopInfoRootView,
            ConstraintSet.TOP,
            11.5f
        )
        margin(
            mTopInfoRootView,
            mCardViewAvatarImageView,
            ConstraintSet.START,
            mTopInfoRootView,
            ConstraintSet.START,
            18.2f
        )
        //nickName
        margin(
            mTopInfoRootView,
            mNickNameTextView,
            ConstraintSet.TOP,
            mCardViewAvatarImageView,
            ConstraintSet.BOTTOM,
            3.9f
        )
        margin(
            mTopInfoRootView,
            mNickNameTextView,
            ConstraintSet.START,
            mTopInfoRootView,
            ConstraintSet.START,
            18.2f
        )
        //phone
        margin(
            mTopInfoRootView,
            mPhoneTextView,
            ConstraintSet.TOP,
            mNickNameTextView,
            ConstraintSet.BOTTOM,
            1.2f
        )

        //qrCodeInfo
        margin(
            mBottomQrCodeRootView,
            mQrCodeImageView,
            ConstraintSet.TOP,
            mBottomQrCodeRootView,
            ConstraintSet.TOP,
            16.9f
        )
        margin(
            mBottomQrCodeRootView,
            mQrCodeTips,
            ConstraintSet.TOP,
            mQrCodeImageView,
            ConstraintSet.BOTTOM,
            9.2f
        )
        margin(
            mRootView,
            mRefreshImageView,
            ConstraintSet.END,
            mRoundBackgroundImageView,
            ConstraintSet.END,
            4.9f
        )
        margin(
            mRootView,
            mRefreshImageView,
            ConstraintSet.BOTTOM,
            mRoundBackgroundImageView,
            ConstraintSet.BOTTOM,
            5.9f
        )

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
        calculateViewHeight(mRoundBackgroundImageView, 139.7f)
        calculateViewHeight(mTopInfoRootView, 51.3f)
        calculateViewHeight(mBottomQrCodeRootView, 88.4f)
        calculateViewHeight(mBottomRealNameRootView, 88.4f)
    }

    private fun initListener() {
        mRefreshImageView.setOnClickListener {
            if (mBottomQrCodeRootView.isVisible) {
                mBottomQrCodeRootView.visibility = View.GONE
                mBottomRealNameRootView.visibility = View.VISIBLE
                mOutAnimatorSet.setTarget(mBottomQrCodeRootView)
                mInAnimatorSet.setTarget(mBottomRealNameRootView)
            } else {
                mBottomQrCodeRootView.visibility = View.VISIBLE
                mBottomRealNameRootView.visibility = View.GONE
                mOutAnimatorSet.setTarget(mBottomRealNameRootView)
                mInAnimatorSet.setTarget(mBottomQrCodeRootView)
            }
            mOutAnimatorSet.start()
            mInAnimatorSet.start()
        }

        //开始认证
        mStartAuthenticationTextView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                ConfirmFragment.TITLE,
                resources.getString(R.string.real_name_authentication_title)
            )
            bundle.putInt(ConfirmFragment.ICON, R.mipmap.icon_id_card)
            bundle.putString(
                ConfirmFragment.TIPS,
                resources.getString(R.string.real_name_authentication_explain)
            )
            bundle.putString(
                ConfirmFragment.BOTTOM_ITEM1,
                getString(R.string.real_name_authentication_protocol)
            )
            bundle.putString(
                ConfirmFragment.BOTTOM_ITEM2,
                getString(R.string.real_name_authentication_agree)
            )
            bundle.putSerializable(ConfirmFragment.TYPE,
                ConfirmFragment.Companion.ConfirmType.REAL_NAME_AUTHORIZATION
            )
            it.findNavController()
                .navigate(R.id.action_QRCodeCardFragment_to_RealNameAuthorizationFragment, bundle)
        }
    }

    private fun initAnimator() {
        mOutAnimatorSet = AnimatorInflater.loadAnimator(
            requireContext(),
            R.animator.anim_qr_card_out
        ) as AnimatorSet
        mInAnimatorSet = AnimatorInflater.loadAnimator(
            requireContext(),
            R.animator.anim_qr_card_in
        ) as AnimatorSet

        val distance = 16000
        val scale = resources.displayMetrics.density * distance
        mBottomQrCodeRootView.cameraDistance = scale
        mBottomRealNameRootView.cameraDistance = scale
    }
}