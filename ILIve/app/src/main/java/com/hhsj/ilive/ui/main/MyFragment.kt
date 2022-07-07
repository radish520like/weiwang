package com.hhsj.ilive.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.main.authentication.IdentityAuthenticationActivity
import com.hhsj.ilive.ui.main.avatar.UpdateAvatarActivity
import com.hhsj.ilive.ui.main.my.UserInfoActivity
import com.hhsj.ilive.ui.main.my.UserQrCodeCardActivity
import com.hhsj.ilive.utils.LogUtils
import com.hhsj.ilive.widget.CustomMyInfoItemView
import com.permissionx.guolindev.PermissionX

/**
 * 我的界面
 * @author YuHan
 */
class MyFragment : BaseFragment() {

    private lateinit var mRootView: ConstraintLayout
    private lateinit var mInfoRootConstraintLayout: ConstraintLayout
    private lateinit var mCustomMyInfoItemView: CustomMyInfoItemView
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mCertificationImageView: ImageView
    private lateinit var mUserNameTextView: TextView
    private lateinit var mPhoneTextView: TextView
    private lateinit var mQrCodeImageView: ImageView
    private lateinit var mSettingImageView: ImageView
    private lateinit var mScrollView: NestedScrollView
    private lateinit var mActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mInfoRootConstraintLayout = view.findViewById(R.id.info_root)
        mAvatarImageView = view.findViewById(R.id.iv_avatar)
        mCertificationImageView = view.findViewById(R.id.iv_certification)
        mCustomMyInfoItemView = view.findViewById(R.id.info_item_setting)
        mUserNameTextView = view.findViewById(R.id.tv_name)
        mPhoneTextView = view.findViewById(R.id.tv_phone)
        mQrCodeImageView = view.findViewById(R.id.iv_qr_code)
        mSettingImageView = view.findViewById(R.id.iv_setting)
        mScrollView = view.findViewById(R.id.scroll_view)

        initMargin()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity

        updateUserInfo()
    }

    private fun updateUserInfo(){
        val header = mActivity.mUserInfoViewModelProvider.getHeader()
        val nickName = mActivity.mUserInfoViewModelProvider.getNickName()
        val phone = mActivity.mUserInfoViewModelProvider.getPhone()
        val token = mActivity.mUserInfoViewModelProvider.getToken()

        Glide.with(this).load(header)
            .apply(RequestOptions().transforms(RoundedCorners(7)))
            .into(mAvatarImageView)
        LogUtils.e("avatar: $header -- phone: $phone")

        mUserNameTextView.text = nickName
        mPhoneTextView.text = phone
    }

    override fun onResume() {
        super.onResume()
        updateUserInfo()
    }

    private fun initMargin(){
    }

    private fun initListener() {
        mSettingImageView.setOnClickListener {
            startUserInfoActivity(false)
        }

        mInfoRootConstraintLayout.setOnClickListener {
            startUserInfoActivity(true)
        }

        mAvatarImageView.setOnClickListener {
            PermissionX.init(activity)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {

                        val intent = Intent(requireContext(),UpdateAvatarActivity::class.java)
                        intent.putExtra(UpdateAvatarActivity.PREVIEW_AVATAR,true)
                        startActivity(intent)

                    } else {
                        Toast.makeText(activity, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
                    }
                }
        }

        mQrCodeImageView.setOnClickListener {
            val intent = Intent(requireContext(),UserQrCodeCardActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                mActivity,mQrCodeImageView,"ImageQrCode"
            )
            startActivity(intent,options.toBundle())
        }

        mCertificationImageView.setOnClickListener {
            val intent = Intent(requireContext(),IdentityAuthenticationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startUserInfoActivity(forUpdate: Boolean){
        val intent = Intent(requireContext(), UserInfoActivity::class.java)
        if(forUpdate){
            intent.putExtra("forUpdate",true);
        }
        startActivity(intent)
    }
}