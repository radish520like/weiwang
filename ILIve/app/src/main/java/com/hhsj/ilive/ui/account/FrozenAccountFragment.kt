package com.hhsj.ilive.ui.account

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
import com.hhsj.ilive.widget.CustomEnableTextView
import com.hhsj.ilive.widget.CustomFontEditText

/**
 * 冻结账户
 * @author YuHan
 */
class FrozenAccountFragment : BaseFragment() {

    private lateinit var mTitleTextView: TextView
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mBackImageView:ImageView
    private lateinit var mFrozenTextView: CustomEnableTextView
    private lateinit var mPhoneEditText: CustomFontEditText
    private lateinit var mNameEditText: CustomFontEditText
    private lateinit var mIdCardEditText: CustomFontEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account_frozen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mTitleTextView = view.findViewById(R.id.tv_title)
        mBackImageView = view.findViewById(R.id.iv_back)
        mFrozenTextView = view.findViewById(R.id.tv_frozen)
        mPhoneEditText = view.findViewById(R.id.et_phone)
        mNameEditText = view.findViewById(R.id.et_name)
        mIdCardEditText = view.findViewById(R.id.et_id_card)

        mFrozenTextView.canClick(true)

        initViewMargin()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initViewMargin() {
        margin(mRootView,mTitleTextView,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,39.2f)

        margin(mRootView,mPhoneEditText,ConstraintSet.TOP,mRootView,ConstraintSet.TOP,66.6f)
        margin(mRootView,mNameEditText,ConstraintSet.TOP,mPhoneEditText,ConstraintSet.BOTTOM,5.3f)
        margin(mRootView,mIdCardEditText,ConstraintSet.TOP,mNameEditText,ConstraintSet.BOTTOM,5.3f)

        margin(mRootView,mFrozenTextView,ConstraintSet.BOTTOM,mRootView,ConstraintSet.BOTTOM,44.2f)
    }

    private fun initListener() {
        mBackImageView.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

}