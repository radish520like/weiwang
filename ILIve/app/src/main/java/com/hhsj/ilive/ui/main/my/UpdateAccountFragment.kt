package com.hhsj.ilive.ui.main.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomEnableTextView
import com.hhsj.ilive.widget.CustomFontEditText
import com.hhsj.ilive.widget.CustomFontTextView
import com.hhsj.ilive.widget.CustomToast

/**
 * 修改账号
 * @author YuHan
 */
class UpdateAccountFragment : BaseFragment() {

    private lateinit var mTitleTextView: TextView
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mBackImageView:ImageView
    private lateinit var mUpdateNickNameTextView: CustomFontTextView
    private lateinit var mUpdateNickNameLimitTextView: CustomFontTextView
    private lateinit var mFrozenTextView: CustomEnableTextView
    private lateinit var mAccountEditText: CustomFontEditText
    private lateinit var mCircleImageView: ImageView
    private lateinit var mActivity: UserInfoActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mTitleTextView = view.findViewById(R.id.tv_title)
        mBackImageView = view.findViewById(R.id.iv_back)
        mFrozenTextView = view.findViewById(R.id.tv_frozen)
        mAccountEditText = view.findViewById(R.id.et_account)
        mUpdateNickNameTextView = view.findViewById(R.id.tv_tips)
        mCircleImageView = view.findViewById(R.id.iv_circle)
        mUpdateNickNameLimitTextView = view.findViewById(R.id.tv_tips_limit)

        mFrozenTextView.canClick(true)
        mFrozenTextView.setCustomDrawable(unEnableDrawable = R.drawable.shape_round_rect_button_unenable_gray)

        initViewMargin()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as UserInfoActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider

        val accountLimit = mUserInfoViewModelProvider.getAccountLimit()
        mUpdateNickNameLimitTextView.text = resources.getString(R.string.update_user_info_nick_name_limit,accountLimit)
        if(accountLimit <= 0){
            mAccountEditText.setHint(R.string.update_user_info_not_modifiable)
        }
        mAccountEditText.isFocusable = (accountLimit > 0)
        mAccountEditText.isFocusableInTouchMode = (accountLimit > 0)
        mFrozenTextView.canClick(accountLimit > 0)
        mFrozenTextView.isClickable = (accountLimit > 0)

    }

    private fun initViewMargin() {
        margin(mRootView,mTitleTextView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,39.2f)
        margin(mRootView,mAccountEditText,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,66.6f)
        margin(mRootView,mUpdateNickNameTextView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,105.3f)
        margin(mRootView,mFrozenTextView,ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,44.2f)
    }

    private fun initListener() {
        mBackImageView.setOnClickListener {
            goBack(it.findNavController())
        }
        mFrozenTextView.setOnClickListener {
            val account = mAccountEditText.text.toString()
            if(account.length < 8 || account.length > 12){
                CustomToast.getInstance(requireContext()).show("长度8~12位")
            }else{
                val regex = Regex("[A-Za-z0-9]+")
                if(regex.matches(account)){
                    //TODO
                }else{
                    CustomToast.getInstance(requireContext()).show("仅可输入数字和字母不区分大小写")
                }
            }

        }
    }

}