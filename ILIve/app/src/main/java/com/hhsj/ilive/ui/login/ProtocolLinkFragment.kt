package com.hhsj.ilive.ui.login

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.BaseFragment
import com.hhsj.ilive.widget.CustomCheckBox

/**
 * 协议界面
 * @author YuHan
 */
class ProtocolLinkFragment: BaseFragment() {

    private lateinit var mCloseImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_protocol, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mContentTextView: TextView = view.findViewById(R.id.tv_content)
        mContentTextView.movementMethod = ScrollingMovementMethod.getInstance()

        mCloseImageView = view.findViewById(R.id.iv_close)

        initListener()
    }

    private fun initListener(){
        mCloseImageView.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}