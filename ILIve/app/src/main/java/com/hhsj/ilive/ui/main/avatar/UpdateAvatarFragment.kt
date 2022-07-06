package com.hhsj.ilive.ui.main.avatar

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.oss_library.OssService
import com.hhsj.ilive.utils.FileUtils
import com.hhsj.ilive.utils.LogUtils
import com.hhsj.ilive.utils.TimeUtils
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomToast
/**
 * 修改头像
 * @author YuHan
 */
class UpdateAvatarFragment: BaseFragment() {

    private lateinit var mRootView: ConstraintLayout
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mBottomLinearLayout: LinearLayout
    private lateinit var mUpdateAvatarTextView: TextView
    private lateinit var mSaveAvatarTextView: TextView

    private lateinit var mActivity: UpdateAvatarActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    private lateinit var mShowAnimation: Animation
    private lateinit var mHideAnimation: Animation

    private lateinit var mOssServer: OssService

    private var mAvatarBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAvatarImageView = view.findViewById(R.id.iv_avatar)
        mRootView = view.findViewById(R.id.root_view)
        mBottomLinearLayout = view.findViewById(R.id.bottom_ll)
        mUpdateAvatarTextView = view.findViewById(R.id.tv_update)
        mSaveAvatarTextView = view.findViewById(R.id.tv_save)

        mShowAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_to_up)
        mHideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.up_to_bottom)

        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as UpdateAvatarActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider

        mOssServer = OssService(requireContext())

        loadUrlWithBitmap(mActivity.mCompressPath,mAvatarImageView){
            mAvatarBitmap = it
        }
    }

    private fun initListener(){
        mRootView.setOnClickListener {
            mActivity.finish()
        }

        mAvatarImageView.setOnClickListener {
            if(mBottomLinearLayout.isVisible){
                mBottomLinearLayout.startAnimation(mHideAnimation)
                mBottomLinearLayout.visibility = View.GONE
            }else{
                mBottomLinearLayout.startAnimation(mShowAnimation)
                mBottomLinearLayout.visibility = View.VISIBLE
            }
        }

        mUpdateAvatarTextView.setOnClickListener {
            uploadAvatarToOSS()
        }

        mSaveAvatarTextView.setOnClickListener {
            mAvatarBitmap?.apply {
                val result = FileUtils.saveImageToGallery(requireContext().applicationContext,this)
                val msg = if(result) resources.getString(R.string.save_picture_success) else resources.getString(R.string.save_picture_failure)
                CustomToast.getInstance(requireContext()).show(msg)
                mBottomLinearLayout.visibility = View.GONE
            }
        }
    }

    private fun uploadAvatarToOSS(){
        mOssServer.setOnUploadListener(object: OssService.OnUploadListener{
            override fun onSuccess(url: String?) {
                url?.apply {
                    mUserInfoViewModelProvider.updateUserInfo(header = this,success = {
                        mActivity.finish()
                    },failure = {
                        CustomToast.getInstance(requireContext()).show(it ?: resources.getString(R.string.error_update_avatar))
                    })
                }
            }

            override fun onFailure() {
                CustomToast.getInstance(requireContext()).show(resources.getString(R.string.error_upload_failure))
            }

            override fun onProgress(percent: Int) {}
        })
        val objectKey = "${TimeUtils.getCurrentDataFormatTime()}/${mUserInfoViewModelProvider.getId()}_head.png"
        LogUtils.e("OSS objectKey $objectKey")
        mOssServer.asyncPutImage(objectKey,mActivity.mOriginalPath)
    }
}