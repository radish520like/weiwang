package com.hhsj.ilive.ui.main.my

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import org.devio.takephoto.model.CropOptions
import org.devio.takephoto.model.TResult
import java.io.File

/**
 * 修改头像
 * @author YuHan
 */
private const val CROP_PHOTO: Int = 1
private const val CHOOSE_PHOTO = 2

class UpdateAvatarFragment : BaseFragment() {

    private lateinit var mRootView: ConstraintLayout
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mAvatarLinearLayout: LinearLayout
    private lateinit var mSaveLinearLayout: LinearLayout
    private lateinit var mBackLinearLayout: LinearLayout
    private lateinit var mBottomView: View

    private lateinit var mActivity: UserInfoActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mAvatarImageView = view.findViewById(R.id.iv_avatar)
        mAvatarLinearLayout = view.findViewById(R.id.ll_avatar)
        mSaveLinearLayout = view.findViewById(R.id.ll_save)
        mBackLinearLayout = view.findViewById(R.id.ll_back)
        mBottomView = view.findViewById(R.id.view_bottom)

        initViewMargin()
        initViewSize()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as UserInfoActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider

        val header = mUserInfoViewModelProvider.getHeader()

        Glide.with(requireContext()).load(header).into(mAvatarImageView)
    }

    private fun initViewMargin() {
        margin(mRootView,mAvatarImageView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,37.2f)
        margin(mRootView,mBottomView,ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,18.2f);
    }

    private fun initViewSize(){
        calculateViewHeight(mAvatarImageView,100.0f)
    }

    private fun initListener() {
        mAvatarLinearLayout.setOnClickListener {
            it.findNavController().navigate(R.id.action_updateAvatarFragment_to_confirmUpdateAvatarFragment)

//            val file = File(mActivity.cacheDir.absoluteFile,"/avatar/${System.currentTimeMillis()}.jpg")
//            if(!file.parentFile.exists()){
//                file.parentFile.mkdirs()
//            }
//            println("abc : file = ${file.absolutePath}")
//            val imageUri = Uri.fromFile(file)
//            val builder: CropOptions.Builder = CropOptions.Builder()
//            builder.setWithOwnCrop(false)
//            val cropOptions = builder.create()
//            cropOptions.aspectX = 9
//            cropOptions.aspectY = 10
//            takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions)
        }

        mSaveLinearLayout.setOnClickListener {

        }

        mBackLinearLayout.setOnClickListener {

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

}