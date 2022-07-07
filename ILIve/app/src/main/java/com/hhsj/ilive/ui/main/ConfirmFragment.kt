package com.hhsj.ilive.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.login.LoginActivity
import com.hhsj.ilive.ui.login.ProtocolLinkFragment
import com.hhsj.ilive.ui.main.avatar.UpdateAvatarActivity
import com.hhsj.ilive.utils.FileUtils
import com.hhsj.ilive.utils.LogUtils
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomToast
import org.devio.takephoto.compress.CompressConfig
import org.devio.takephoto.model.CropOptions
import org.devio.takephoto.model.TResult
import java.io.File

/**
 * 确定界面
 * @author YuHan
 */
class ConfirmFragment : BaseFragment() {

    companion object{
        @JvmStatic
        val TITLE = "TITLE"
        val ICON = "ICON"
        val TIPS = "tips"
        val BOTTOM_ITEM1 = "bottom_item1"
        val BOTTOM_ITEM2 = "bottom_item2"
        val TYPE = "type"

        enum class ConfirmType{
            UPDATE_PHONE,
            UPDATE_AVATAR,
            REAL_NAME_AUTHORIZATION
        }
    }

    private lateinit var mTitleTextView: TextView
    private lateinit var mLogoImageView: ImageView
    private lateinit var mExplainTextView: TextView
    private lateinit var mBottomView: View
    private lateinit var mBottomLinearLayout: LinearLayout
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mConfirmTextView: TextView
    private lateinit var mTipsTextView: TextView
    private lateinit var mCancelTextView: TextView
    private lateinit var mActivity: BaseActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    private var mFromType: ConfirmType? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mBottomLinearLayout = view.findViewById(R.id.bottom_ll)
        mTitleTextView = view.findViewById(R.id.tv_title)
        mLogoImageView = view.findViewById(R.id.iv_logo)
        mBottomView = view.findViewById(R.id.bottom_view)
        mExplainTextView = view.findViewById(R.id.tv_explain)

        mTipsTextView = view.findViewById(R.id.tv_tips)
        mConfirmTextView = view.findViewById(R.id.tv_confirm)
        mCancelTextView = view.findViewById(R.id.tv_cancel)

        initViewMargin()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as BaseActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider

        val title = arguments?.getString(TITLE) ?: resources.getString(R.string.update_user_info_phone_confirm_title)
        val icon = arguments?.getInt(ICON) ?: R.mipmap.icon_logout
        val explain = arguments?.getString(TIPS) ?: resources.getString(R.string.update_user_info_phone_confirm_tips)
        val bottomItem1 = arguments?.getString(BOTTOM_ITEM1) ?: mUserInfoViewModelProvider.getPhone()
        val bottomItem2 = arguments?.getString(BOTTOM_ITEM2) ?: resources.getString(R.string.update_user_info_phone_confirm)
        mFromType = arguments?.get(TYPE) as ConfirmType?

        mTitleTextView.text = title
        mLogoImageView.setImageResource(icon)
        mExplainTextView.text = explain
        mTipsTextView.text = bottomItem1
        mConfirmTextView.text = bottomItem2

    }

    private fun initViewMargin() {
        margin(mRootView,mTitleTextView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,39.2f)
        margin(mRootView,mLogoImageView,ConstraintSet.TOP,mTitleTextView,ConstraintSet.BOTTOM,15.2f)
        margin(mRootView,mExplainTextView,ConstraintSet.TOP,mLogoImageView,ConstraintSet.BOTTOM,10.2f)
    }

    private fun initListener() {
        mRootView.setOnClickListener {
            hideSoft()
        }
        mTipsTextView.setOnClickListener {
            if(mFromType == ConfirmType.UPDATE_AVATAR){
                val bundle = Bundle()
                bundle.putString(ProtocolLinkFragment.TITLE,resources.getString(R.string.update_user_info_update_avatar_protocol_title))
                bundle.putString(ProtocolLinkFragment.CONTENT,resources.getString(R.string.update_user_info_update_avatar_protocol_content))
                it.findNavController().navigate(R.id.action_confirmUpdateAvatarFragment_to_UpdateAvatarProtocolLinkFragment,bundle)
            }
        }
        mConfirmTextView.setOnClickListener {
            when(mFromType){
                ConfirmType.UPDATE_AVATAR -> cropPicture()
                ConfirmType.UPDATE_PHONE -> {
                    val phone = mTipsTextView.text.toString()
                    mUserInfoViewModelProvider.updateUserInfo(phone = phone,success = {
                        mActivity.finish()
                    },failure = {
                        CustomToast.getInstance(requireContext()).show(it ?: resources.getString(R.string.error_update_phone))
                    })
                }
                ConfirmType.REAL_NAME_AUTHORIZATION -> {}
                else -> {
                    mUserInfoViewModelProvider.logout({
                        LogUtils.e("logout Success")
                    }, {})
                    val intent = Intent(activity, LoginActivity::class.java)
                    intent.putExtra("fromLogOutPage",true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }
        mCancelTextView.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun cropPicture(){
        val imageUri = FileUtils.getAvatarUri("${System.currentTimeMillis()}.jpg")
        val builder: CropOptions.Builder = CropOptions.Builder()
        builder.setWithOwnCrop(false)
        val config = CompressConfig.Builder().setMaxSize(102400)
            .setMaxPixel(mScreenWidthPixels)
            //拍照压缩后是否保存原图
            .enableReserveRaw(false)
            .create()
        val cropOptions = builder.create()
        cropOptions.aspectX = 9
        cropOptions.aspectY = 10
        takePhoto.onEnableCompress(config, true)
        takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions)
    }

    override fun takeSuccess(result: TResult?) {
        result?.apply {
            val image = result.image
            val intent = Intent(requireContext(),UpdateAvatarActivity::class.java)
            intent.putExtra(UpdateAvatarActivity.PREVIEW_AVATAR,false)
            intent.putExtra(UpdateAvatarActivity.COMPRESS_PATH,image.compressPath)
            intent.putExtra(UpdateAvatarActivity.ORIGINAL_PATH,image.originalPath)
            startActivity(intent)
        }
    }

    override fun takeFail(result: TResult?, msg: String?) {
        LogUtils.e("获取照片失败 $msg")
    }

    override fun takeCancel() {
        LogUtils.e("取消获取照片失败")
    }
}