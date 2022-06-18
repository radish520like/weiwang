package com.hhsj.ilive.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import com.hhsj.ilive.AccountSecurityActivity
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.BaseFragment
import com.hhsj.ilive.utils.LogUtils
import com.hhsj.ilive.viewmodels.UserInfoViewModel
import com.hhsj.ilive.widget.CustomToast
import com.hhsj.ilive.widget.CustomVerifyCodeView
import kotlin.math.floor

/**
 * 验证码界面
 * @author YuHan
 */
class VerifyCodeFragment : BaseFragment() {

    private lateinit var mVerifyCode: CustomVerifyCodeView
    private lateinit var mVerifyCodeTip: TextView
    private lateinit var mTitleTextView: TextView
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mVerifyCodeMsgTextView: TextView
    private lateinit var mProgress: ProgressBar
    private lateinit var mBackImageView: ImageView
    private lateinit var mHelpImageView: ImageView
    private lateinit var mUserInfoViewModelProvider: UserInfoViewModel
    private val mCountDownTimer by lazy {
        object : CountDownTimer(59999, 1000) {
            override fun onTick(value: Long) {
                mVerifyCodeMsgTextView.isClickable = false
                mVerifyCodeMsgTextView.text = resources.getString(
                    R.string.verify_code_count_down,
                    floor(value.toDouble() / 1000).toInt().toString()
                )
            }

            override fun onFinish() {
                mVerifyCodeMsgTextView.isClickable = true
                mVerifyCodeMsgTextView.text = resources.getString(R.string.verify_code_retrieve)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verify_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mProgress = view.findViewById(R.id.progress)
        mTitleTextView = view.findViewById(R.id.tv_title)
        mHelpImageView = view.findViewById(R.id.iv_help)
        mVerifyCode = view.findViewById(R.id.verify_code)
        mBackImageView = view.findViewById(R.id.iv_back)
        mVerifyCodeTip = view.findViewById(R.id.tv_verify_code_tip)
        mVerifyCodeMsgTextView = view.findViewById(R.id.tv_verify_code_msg)

        initMargin()
        initListener()
    }

    private fun initMargin() {
        margin(mRootView, mTitleTextView, ConstraintSet.TOP, mRootView, ConstraintSet.TOP, 39.2f)
        margin(mRootView,mVerifyCode,ConstraintSet.TOP,mVerifyCodeTip,ConstraintSet.BOTTOM,11.7f)
        margin(mRootView,mVerifyCodeMsgTextView,ConstraintSet.TOP,mVerifyCode,ConstraintSet.BOTTOM,11.7f)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mUserInfoViewModelProvider = (activity as BaseActivity).mUserInfoViewModelProvider
        //获取验证码
        mUserInfoViewModelProvider.getVerifyCode({ LogUtils.e("getVerifyCode success") },{ LogUtils.e(it)})
        val fromLogOut = arguments?.getBoolean("fromLogOut")
        if(fromLogOut != null && fromLogOut){
            mVerifyCodeTip.text =
                getString(R.string.verify_code_tips, mUserInfoViewModelProvider.getPhone().replaceRange(3..6,"****"))
        }else{
            mVerifyCodeTip.text =
                getString(R.string.verify_code_tips, mUserInfoViewModelProvider.getPhone())
        }

        mCountDownTimer.start()
    }

    private fun initListener() {
        //验证码输入完毕
        mVerifyCode.setOnCompletionListener {
            mUserInfoViewModelProvider.registerOrLogin(mUserInfoViewModelProvider.getPhone(), it, {
                mProgress.visibility = View.GONE
                mVerifyCode.findNavController().navigate(R.id.action_verifyCodeFragment_to_mainActivity)
                activity?.finish()
            }, { errorMsg ->
                mVerifyCode.clear()
                mProgress.visibility = View.GONE
                errorMsg?.let {
                    CustomToast.getInstance(requireContext().applicationContext)
                        .show(errorMsg)
                }
            })
        }

        //重新获取验证码
        mVerifyCodeMsgTextView.setOnClickListener {
            mCountDownTimer.start()
            mVerifyCode.clear()
            mUserInfoViewModelProvider.getVerifyCode({ LogUtils.e("getVerifyCode success") },{ LogUtils.e(it)})
        }
        mBackImageView.setOnClickListener {
            mBackImageView.findNavController().popBackStack()
        }

        mHelpImageView.setOnClickListener {
            val intent = Intent(activity, AccountSecurityActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        showSoft(mVerifyCode.getEditText())
    }

    override fun onPause() {
        super.onPause()
        hideSoftWithEditText(mVerifyCode.getEditText())
    }

    override fun onDestroy() {
        super.onDestroy()
        mVerifyCode.release()
        mCountDownTimer.cancel()
    }
}