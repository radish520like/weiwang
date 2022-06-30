package com.hhsj.ilive.ui.main.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R

/**
 * 二维码名片
 * @author YuHan
 */
class QRCodeCardFragment : BaseFragment() {

    private lateinit var mRootView: ConstraintLayout
    private lateinit var mInfoRootView: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr_code_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        mInfoRootView = view.findViewById(R.id.info_root)

        initMargin()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun initMargin(){
        margin(mRootView,mInfoRootView, ConstraintSet.TOP,mRootView,ConstraintSet.TOP,30.8f)
        margin(mRootView,mInfoRootView, ConstraintSet.START,mRootView,ConstraintSet.START,6.4f)
        margin(mRootView,mInfoRootView, ConstraintSet.END,mRootView,ConstraintSet.END,6.4f)

    }
}