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
import com.hhsj.ilive.*
import com.hhsj.ilive.ui.main.ConfirmFragment
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
    private lateinit var mFromLogKey: String
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
        margin(
            mRootView,
            mVerifyCode,
            ConstraintSet.TOP,
            mVerifyCodeTip,
            ConstraintSet.BOTTOM,
            11.7f
        )
        margin(
            mRootView,
            mVerifyCodeMsgTextView,
            ConstraintSet.TOP,
            mVerifyCode,
            ConstraintSet.BOTTOM,
            11.7f
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mUserInfoViewModelProvider = (activity as BaseActivity).mUserInfoViewModelProvider
        //获取验证码
        mFromLogKey = arguments?.getString(VERIFY_CODE_FROM_KEY) ?: ""
        if (mFromLogKey.isEmpty() || mFromLogKey == VERIFY_CODE_FROM_LOGOUT) {
            //登出切换账号时获取验证码
            mUserInfoViewModelProvider.getVerifyCode({ LogUtils.e("getVerifyCode success") },
                { LogUtils.e(it) })
            if (mFromLogKey == VERIFY_CODE_FROM_LOGOUT) {
                getString(
                    R.string.verify_code_tips,
                    mUserInfoViewModelProvider.getPhone().replaceRange(3..6, "****")
                )
            }
        } else if (mFromLogKey == VERIFY_CODE_FROM_UPDATE_PHONE_OLD) {
            //校验手机号
            mUserInfoViewModelProvider.getVerifyCodeExpectLoginOrRegister("", {}, {})
        } else if (mFromLogKey == VERIFY_CODE_FROM_UPDATE_PHONE_NEW) {
            // 新手机号获取验证码在上一步请求中已获取，需要获取 server 端返回值，判断新手机可以绑定。
        }
        mVerifyCodeTip.text =
            getString(R.string.verify_code_tips, mUserInfoViewModelProvider.getPhone())

        mCountDownTimer.start()
    }

    private fun initListener() {
        //验证码输入完毕
        mVerifyCode.setOnCompletionListener {
            when (mFromLogKey) {
                VERIFY_CODE_FROM_UPDATE_PHONE_OLD -> {
                    //验证旧手机号
                    mUserInfoViewModelProvider.checkPhone("", it, success = {
                        mVerifyCode.clear()
                        mVerifyCode.findNavController()
                            .navigate(R.id.action_verifyCodeFragment_to_updatePhoneFragment)
                    }, failure = {
                        mVerifyCode.clear()
                    })
                }
                VERIFY_CODE_FROM_UPDATE_PHONE_NEW -> {
                    //验证新手机号
                    arguments?.getString(UPDATE_NEW_PHONE)?.apply {
                        mUserInfoViewModelProvider.checkPhone(this, it, success = {
                            mVerifyCode.clear()
                            val bundle = initConfirmFragmentBundle(
                                title = resources.getString(R.string.update_user_info_update_avatar_title),
                                picture = R.mipmap.icon_person,
                                tips = resources.getString(R.string.update_user_info_update_avatar_tips),
                                bottom1 = this,
                                bottom2 = resources.getString(R.string.update_user_info_phone_confirm),
                                confirmType = ConfirmFragment.Companion.ConfirmType.UPDATE_PHONE
                            )

                            mVerifyCode.findNavController()
                                .navigate(
                                    R.id.action_verifyCodeFragment_newPhone_to_confirmUpdatePhoneFragment,
                                    bundle
                                )
                        }, failure = {
                            mVerifyCode.clear()
                        })
                    }
                }
                else -> {
                    mUserInfoViewModelProvider.registerOrLogin(
                        mUserInfoViewModelProvider.getPhone(),
                        it,
                        {
                            mProgress.visibility = View.GONE
                            mVerifyCode.findNavController()
                                .navigate(R.id.action_verifyCodeFragment_to_mainActivity)
                            activity?.finish()
                        },
                        { errorMsg ->
                            mVerifyCode.clear()
                            mProgress.visibility = View.GONE
                            errorMsg?.let {
                                CustomToast.getInstance(requireContext().applicationContext)
                                    .show(errorMsg)
                            }
                        })
                }
            }
        }

        //重新获取验证码
        mVerifyCodeMsgTextView.setOnClickListener {
            mCountDownTimer.start()
            mVerifyCode.clear()
            mUserInfoViewModelProvider.getVerifyCode({ LogUtils.e("getVerifyCode success") },
                { LogUtils.e(it) })
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