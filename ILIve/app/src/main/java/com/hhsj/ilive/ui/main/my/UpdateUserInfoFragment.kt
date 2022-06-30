package com.hhsj.ilive.ui.main.my

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.main.update.UpdatePhoneActivity
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomMyInfoItemView
import org.devio.takephoto.app.TakePhotoFragment
import org.devio.takephoto.model.CropOptions
import org.devio.takephoto.model.TResult
import java.io.File

/**
 * 用户信息-修改
 * @author YuHan
 */
private const val CROP_PHOTO: Int = 1
private const val CHOOSE_PHOTO = 2

class UpdateUserInfoFragment : TakePhotoFragment() {

    private lateinit var mBackImageView: ImageView
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mUpdatePhoneImageView: ImageView
    private lateinit var mPhoneTextView: TextView
    private lateinit var mUserNameTextView: TextView
    private lateinit var mUserInfoNickName: CustomMyInfoItemView
    private lateinit var mUserInfoAccount: CustomMyInfoItemView
    private lateinit var mActivity: UserInfoActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_info_forupdate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBackImageView = view.findViewById(R.id.iv_back)
        mUserNameTextView = view.findViewById(R.id.tv_name)
        mAvatarImageView = view.findViewById(R.id.iv_avatar)
        mPhoneTextView = view.findViewById(R.id.tv_phone)
        mUpdatePhoneImageView = view.findViewById(R.id.iv_update_phone)

        mUserInfoNickName = view.findViewById(R.id.user_info_nick_name)
        mUserInfoAccount = view.findViewById(R.id.user_info_account)

        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as UserInfoActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider

        mUserInfoViewModelProvider.getUserInfo({
            Glide.with(requireContext()).load(it.header).into(mAvatarImageView)
            mPhoneTextView.text = it.phone
            mUserNameTextView.text = it.nickName

            mUserInfoNickName.setValue(it.nickName)
            mUserInfoAccount.setValue(it.account)
        },{})
    }

    private fun initListener() {
        mBackImageView.setOnClickListener {
            mActivity.finish()
        }

        mPhoneTextView.setOnClickListener {
            jumpToUpdatePhoneActivity()
        }

        mUpdatePhoneImageView.setOnClickListener {
            jumpToUpdatePhoneActivity()
        }

        mUserInfoNickName.setOnClickListener{
            it.findNavController().navigate(R.id.action_userInfoForUpdateFragment_to_updateNickNameFragment)
        }

        mUserInfoAccount.setOnClickListener {
            it.findNavController().navigate(R.id.action_userInfoForUpdateFragment_to_updateAccountFragment)
        }

        mAvatarImageView.setOnClickListener {
//            val file = File(
//                Environment.getExternalStorageDirectory(),
//                "/temp/" + System.currentTimeMillis() + ".jpg"
//            )
//            if (!file.parentFile.exists()) {
//                file.parentFile.mkdirs()
//            }
            val file = File(mActivity.cacheDir.absoluteFile,"/avatar/${System.currentTimeMillis()}.jpg")
            if(!file.parentFile.exists()){
                file.parentFile.mkdirs()
            }
            println("abc : file = ${file.absolutePath}")
            val imageUri = Uri.fromFile(file)
            val builder: CropOptions.Builder = CropOptions.Builder()
            builder.setWithOwnCrop(false)
            val cropOptions = builder.create()
            cropOptions.aspectX = 9
            cropOptions.aspectY = 10
            takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions)
        }
    }

    override fun takeSuccess(result: TResult?) {
        super.takeSuccess(result)
        println("abc : 获取照片成功")
    }

    override fun takeFail(result: TResult?, msg: String?) {
        super.takeFail(result, msg)
        println("abc : 获取照片失败")
    }

    override fun takeCancel() {
        super.takeCancel()
        println("abc : 获取照片取消")
    }

    private fun jumpToUpdatePhoneActivity(){
        val intent = Intent(requireContext(),UpdatePhoneActivity::class.java)
        startActivity(intent)
    }
}