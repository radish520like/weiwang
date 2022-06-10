package com.hhsj.ilive.ui.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hhsj.ilive.MainActivity
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.BaseFragment
import com.hhsj.ilive.ui.main.my.UserInfoActivity
import com.hhsj.ilive.utils.LogUtils
import com.hhsj.ilive.widget.CustomMyInfoItemView

/**
 * 我的界面
 * @author YuHan
 */
class MyFragment: BaseFragment() {

    private lateinit var mInfoRootConstraintLayout: ConstraintLayout
    private lateinit var mCustomMyInfoItemView: CustomMyInfoItemView
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mUserNameTextView: TextView
    private lateinit var mPhoneTextView: TextView
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
        mInfoRootConstraintLayout = view.findViewById(R.id.info_root)
        mAvatarImageView = view.findViewById(R.id.iv_avatar)
        mCustomMyInfoItemView = view.findViewById(R.id.info_item_setting)
        mUserNameTextView = view.findViewById(R.id.tv_name)
        mPhoneTextView = view.findViewById(R.id.tv_phone)

        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity

        val header = mActivity.mUserInfoViewModelProvider.getHeader()
        val nickName = mActivity.mUserInfoViewModelProvider.getNickName()
        val phone = mActivity.mUserInfoViewModelProvider.getPhone()
        val token = mActivity.mUserInfoViewModelProvider.getToken()

        Glide.with(this).load(header).into(mAvatarImageView)
        LogUtils.e("avatar: $header")
//        Glide.with(mUserNameTextView.context).load(header).addListener(object: RequestListener<Drawable>{
//            override fun onLoadFailed(
//                e: GlideException?,
//                model: Any?,
//                target: Target<Drawable>?,
//                isFirstResource: Boolean
//            ): Boolean {
//                println("aaaa loadFailre ${e?.toString()} --- ${e?.printStackTrace()}")
//                println("aaaa header $header")
//                return true
//            }
//
//            override fun onResourceReady(
//                resource: Drawable?,
//                model: Any?,
//                target: Target<Drawable>?,
//                dataSource: DataSource?,
//                isFirstResource: Boolean
//            ): Boolean {
//                println("abc : bbbbbb")
//                return true
//            }
//
//        }).into(mAvatarImageView)
        mUserNameTextView.text = nickName
        mPhoneTextView.text = phone.replaceRange(3..6,"****")
    }

    private fun initListener(){
        mCustomMyInfoItemView.setOnClickListener {
            val intent = Intent(requireContext(),UserInfoActivity::class.java)
            startActivity(intent)
        }
    }
}