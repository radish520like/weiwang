package com.hhsj.ilive.ui.main.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.hhsj.ilive.*
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomEnableTextView
import com.hhsj.ilive.widget.CustomFontEditText
import com.hhsj.ilive.widget.CustomFontTextView
import com.hhsj.ilive.widget.CustomToast

/**
 * 修改手机号输入界面
 * @author YuHan
 */
class UpdatePhoneFragment : BaseFragment() {

    private lateinit var mTitleTextView: TextView
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mBackImageView:ImageView
    private lateinit var mUpdateTipsTextView: CustomFontTextView
    private lateinit var mUpdateTipsLimitTextView: CustomFontTextView
    private lateinit var mFrozenTextView: CustomEnableTextView
    private lateinit var mPhoneEditText: CustomFontEditText
    private lateinit var mActivity: BaseActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mTitleTextView = view.findViewById(R.id.tv_title)
        mBackImageView = view.findViewById(R.id.iv_back)
        mFrozenTextView = view.findViewById(R.id.tv_frozen)
        mPhoneEditText = view.findViewById(R.id.et_phone)
        mUpdateTipsTextView = view.findViewById(R.id.tv_tips)
        mUpdateTipsLimitTextView = view.findViewById(R.id.tv_tips_limit)

        mFrozenTextView.canClick(true)
        mFrozenTextView.setCustomDrawable(unEnableDrawable = R.drawable.shape_round_rect_button_unenable_gray)

        initViewMargin()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as BaseActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider
    }

    private fun initViewMargin() {
        margin(mRootView,mTitleTextView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,39.2f)
        margin(mRootView,mPhoneEditText,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,66.7f)
        margin(mRootView,mUpdateTipsTextView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,88.8f)
        margin(mRootView,mFrozenTextView,ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,44.2f)
    }

    private fun initListener() {
        mRootView.setOnClickListener {
            hideSoft()
        }

        mBackImageView.setOnClickListener {
            goBack(it.findNavController())
        }
        mPhoneEditText.addTextChangedListener {
            val input = it?.toString()
            input?.apply {
                if(this.length >= 11){
                    hideSoftWithEditText(mPhoneEditText)
                }
            }
        }
        mFrozenTextView.setOnClickListener {
            val phone = mPhoneEditText.text.toString()
            if(checkPhone(phone)){
                CustomToast.getInstance(requireContext().applicationContext)
                    .show(resources.getString(R.string.toast_telephone_error))
            }else{
                val bundle = Bundle()
                bundle.putString(UPDATE_NEW_PHONE,phone)
                bundle.putString(VERIFY_CODE_FROM_KEY, VERIFY_CODE_FROM_UPDATE_PHONE_NEW)
                it.findNavController().navigate(R.id.action_updatePhoneFragment_to_verifyCodeFragment_newPhone,bundle)
            }
        }
    }

}