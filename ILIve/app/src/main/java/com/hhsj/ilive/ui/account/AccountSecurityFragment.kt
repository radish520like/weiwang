package com.hhsj.ilive.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R
import com.hhsj.ilive.widget.CustomEnableTextView

/**
 * 账户安全
 * @author YuHan
 */
class AccountSecurityFragment : BaseFragment() {

    private lateinit var mTitleTextView: TextView
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mBackImageView:ImageView
    private lateinit var mFrozenTextView: CustomEnableTextView
    private lateinit var mThawTextView: CustomEnableTextView
    private lateinit var mAppealTextView: CustomEnableTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account_security, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mTitleTextView = view.findViewById(R.id.tv_title)
        mBackImageView = view.findViewById(R.id.iv_back)
        mFrozenTextView = view.findViewById(R.id.tv_frozen)
        mThawTextView = view.findViewById(R.id.tv_thaw)
        mAppealTextView = view.findViewById(R.id.tv_appeal)

        mFrozenTextView.canClick(true)
        mThawTextView.canClick(true)
        mAppealTextView.canClick(true)

        initViewMargin()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initViewMargin() {
        margin(mRootView,mTitleTextView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,39.2f)
        margin(mRootView,mFrozenTextView,ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,86.4f)
        margin(mRootView,mThawTextView,ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,65.3f)
        margin(mRootView,mAppealTextView,ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,44.2f)
    }

    private fun initListener() {
        mBackImageView.setOnClickListener {
            activity?.finish()
        }

//        mFrozenTextView.setOnClickListener {
//            it.findNavController().navigate(R.id.action_accountSecurityFragment_to_frozenAccountFragment)
//        }
//        mThawTextView.setOnClickListener {
//            it.findNavController().navigate(R.id.action_accountSecurityFragment_to_thawAccountFragment)
//        }
//        mAppealTextView.setOnClickListener {
//            it.findNavController().navigate(R.id.action_accountSecurityFragment_to_appealAccountFragment)
//        }
    }

}