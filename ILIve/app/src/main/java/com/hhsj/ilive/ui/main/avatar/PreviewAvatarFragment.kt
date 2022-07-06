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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.utils.FileUtils
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomFontTextView
import com.hhsj.ilive.widget.CustomToast

/**
 * 头像预览
 * @author YuHan
 */
class PreviewAvatarFragment: BaseFragment() {

    private lateinit var mRootView: ConstraintLayout
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mBottomLinearLayout: LinearLayout
    private lateinit var mSaveTextView: CustomFontTextView

    private lateinit var mActivity: BaseActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    private lateinit var mShowAnimation: Animation
    private lateinit var mHideAnimation: Animation
    private var mAvatarBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preview_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAvatarImageView = view.findViewById(R.id.iv_avatar)
        mRootView = view.findViewById(R.id.root_view)
        mBottomLinearLayout = view.findViewById(R.id.bottom_ll)
        mSaveTextView = view.findViewById(R.id.tv_save)

        mShowAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_to_up)
        mHideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.up_to_bottom)

        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as BaseActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider

        val header = mUserInfoViewModelProvider.getHeader()

        loadUrlWithBitmap(header,mAvatarImageView){
            mAvatarBitmap = it
        }
    }

    private fun initListener(){
        mRootView.setOnClickListener {
            mActivity.finish()
        }

        mSaveTextView.setOnClickListener {
            mAvatarBitmap?.apply {
                val result = FileUtils.saveImageToGallery(requireContext().applicationContext,this)
                val msg = if(result) resources.getString(R.string.save_picture_success) else resources.getString(R.string.save_picture_failure)
                CustomToast.getInstance(requireContext()).show(msg)
                mBottomLinearLayout.visibility = View.GONE
            }
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
    }
}