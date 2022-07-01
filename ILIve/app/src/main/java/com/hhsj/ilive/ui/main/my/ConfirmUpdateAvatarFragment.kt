package com.hhsj.ilive.ui.main.my

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
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.login.ProtocolLinkFragment
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import org.devio.takephoto.model.CropOptions
import org.devio.takephoto.model.TResult
import java.io.File

/**
 * 确认修改头像
 * @author YuHan
 */
class ConfirmUpdateAvatarFragment : BaseFragment() {

    private lateinit var mTitleTextView: TextView
    private lateinit var mLogoImageView: ImageView
    private lateinit var mExplainTextView: TextView
    private lateinit var mBottomView: View
    private lateinit var mBottomLinearLayout: LinearLayout
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mAgreeTextView: TextView
    private lateinit var mProtocolTextView: TextView
    private lateinit var mCancelTextView: TextView
    private lateinit var mActivity: UserInfoActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_avatar_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mBottomLinearLayout = view.findViewById(R.id.bottom_ll)
        mTitleTextView = view.findViewById(R.id.tv_title)
        mLogoImageView = view.findViewById(R.id.iv_logo)
        mBottomView = view.findViewById(R.id.bottom_view)
        mExplainTextView = view.findViewById(R.id.tv_explain)

        mAgreeTextView = view.findViewById(R.id.tv_agree)
        mProtocolTextView = view.findViewById(R.id.tv_protocol)
        mCancelTextView = view.findViewById(R.id.tv_cancel)

        initViewMargin()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as UserInfoActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider
    }

    private fun initViewMargin() {
        margin(mRootView,mTitleTextView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,39.2f)
        margin(mRootView,mLogoImageView,ConstraintSet.TOP,mTitleTextView,ConstraintSet.BOTTOM,15.2f)
        margin(mRootView,mExplainTextView,ConstraintSet.TOP,mLogoImageView,ConstraintSet.BOTTOM,10.2f)
    }

    private fun initListener() {

        mProtocolTextView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(ProtocolLinkFragment.TITLE,R.string.update_user_info_update_avatar_protocol_title)
            bundle.putInt(ProtocolLinkFragment.CONTENT,R.string.update_user_info_update_avatar_protocol_content)
            it.findNavController().navigate(R.id.action_confirmUpdateAvatarFragment_to_UpdateAvatarProtocolLinkFragment,bundle)
        }

        mAgreeTextView.setOnClickListener {
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

}