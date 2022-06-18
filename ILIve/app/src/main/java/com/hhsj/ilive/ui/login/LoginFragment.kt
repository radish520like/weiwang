package com.hhsj.ilive.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.hhsj.ilive.AccountSecurityActivity
import com.hhsj.ilive.LoginActivity
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.BaseFragment
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomCheckBoxImageView
import com.hhsj.ilive.widget.CustomEnableTextView
import com.hhsj.ilive.widget.CustomToast

/**
 * 登陆界面
 * @author YuHan
 */
class LoginFragment : BaseFragment() {

    private lateinit var mTelephoneEditText: EditText
    private lateinit var mReadTextView: TextView
    private lateinit var mLoginButton: CustomEnableTextView
    private lateinit var mTitleTextView: TextView
    private lateinit var mDeleteImageView: ImageView
    private lateinit var mHelpImageView: ImageView
    private lateinit var mExplainTextView: TextView
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mLineView: View
    private lateinit var mBackImageView: ImageView
    private lateinit var mReadCheckBox: CustomCheckBoxImageView
    private lateinit var mActivity: LoginActivity
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mLineView = view.findViewById(R.id.line_view)
        mExplainTextView = view.findViewById(R.id.tv_explain)
        mDeleteImageView = view.findViewById(R.id.iv_delete)
        mTitleTextView = view.findViewById(R.id.tv_title)
        mBackImageView = view.findViewById(R.id.iv_back)
        mTelephoneEditText = view.findViewById(R.id.et_telephone)
        mLoginButton = view.findViewById(R.id.btn_login)
        mReadTextView = view.findViewById(R.id.tv_read)
        mHelpImageView = view.findViewById(R.id.iv_help)
        mReadCheckBox = view.findViewById(R.id.checkbox_read)

        val isSwitchAccount = arguments?.getBoolean("fromSwitchAccount")
        if(isSwitchAccount != null && isSwitchAccount){
            mBackImageView.visibility = View.VISIBLE
        }else{
            mBackImageView.visibility = View.GONE
        }

        mTelephoneEditText.isLongClickable = false
        initViewMargin()
        initReadText()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as LoginActivity
        mUserInfoViewModelProvider = mActivity.mUserInfoViewModelProvider

        mReadCheckBox.mIsChecked = mUserInfoViewModelProvider.readChecked.value!!
        mLoginButton.canClick(mUserInfoViewModelProvider.readChecked.value!!)

    }

    private fun initViewMargin() {
        margin(mRootView,mTitleTextView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,39.2f)
        margin(mRootView,mTelephoneEditText,ConstraintSet.TOP,mTitleTextView,ConstraintSet.BOTTOM,17.9f)
        margin(mRootView,mReadTextView,ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,67.6f)
        margin(mRootView,mLineView,ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,44.1f)
    }

    private fun initListener() {
        mReadCheckBox.onCheckedChangeListener {
            mUserInfoViewModelProvider.readChecked.value = it
            enableLogin(it)
        }

        mRootView.setOnClickListener {
            hideSoft()
            CustomToast.getInstance(requireContext()).hide()
        }

        mHelpImageView.setOnClickListener {
            val intent = Intent(activity,AccountSecurityActivity::class.java)
            startActivity(intent)
        }

        mLoginButton.setOnClickListener {
            val text = mTelephoneEditText.text.toString()
            if (text.length != 11 || text[0] != '1' || text.toLongOrNull() == null) {
                CustomToast.getInstance(requireContext().applicationContext)
                    .show(resources.getString(R.string.toast_telephone_error))
            } else if(!mReadCheckBox.mIsChecked){
                CustomToast.getInstance(requireContext().applicationContext)
                    .show(resources.getString(R.string.toast_read_server_clause))
            } else {
                mUserInfoViewModelProvider.phoneNumber.value = text
                it.findNavController().navigate(R.id.action_loginFragment_to_verifyCodeFragment)
            }
        }

        mDeleteImageView.setOnClickListener {
            mTelephoneEditText.setText("")
        }

        mTelephoneEditText.addTextChangedListener {
            val content = it?.toString()
            if (content?.isNotEmpty()!!) {
                mDeleteImageView.visibility = View.VISIBLE
            } else {
                mDeleteImageView.visibility = View.GONE
            }
            if(content.length == 11){
                hideSoft()
            }
        }

        mBackImageView.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun initReadText() {
        val text = resources.getString(R.string.register_read_text)
        val mStyledText = SpannableString(text)

        mStyledText.setSpan(object : ClickableSpan() {
            override fun onClick(p0: View) {
                jumpToProtocolPage(p0.findNavController())
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ResourcesCompat.getColor(resources,R.color.round_rect_button_enable,resources.newTheme())
                ds.isUnderlineText = false
            }
        }, 7, 17, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        mStyledText.setSpan(object : ClickableSpan() {
            override fun onClick(p0: View) {
                jumpToProtocolPage(p0.findNavController())
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ResourcesCompat.getColor(resources,R.color.round_rect_button_enable,resources.newTheme())
                ds.isUnderlineText = false
            }
        }, 18, 24, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        mStyledText.setSpan(object : ClickableSpan() {
            override fun onClick(p0: View) {
                jumpToProtocolPage(p0.findNavController())
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ResourcesCompat.getColor(resources,R.color.round_rect_button_enable,resources.newTheme())
                ds.isUnderlineText = false
            }
        }, 25, 37, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        mReadTextView.text = mStyledText
        mReadTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun jumpToProtocolPage(navController: NavController){
        navController.navigate(R.id.action_loginFragment_to_protocolLinkFragment)
    }

    private fun enableLogin(isChecked: Boolean) {
        mLoginButton.canClick(isChecked)
    }
}