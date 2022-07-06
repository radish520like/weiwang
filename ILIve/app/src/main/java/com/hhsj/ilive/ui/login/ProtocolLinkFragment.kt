package com.hhsj.ilive.ui.login

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R

/**
 * 协议界面
 * @author YuHan
 */
class ProtocolLinkFragment: BaseFragment() {

    companion object{
        @JvmStatic
        val TITLE = "title"
        @JvmStatic
        val CONTENT = "content"
    }

    private lateinit var mCloseImageView: ImageView
    private lateinit var mTitleTextView: TextView
    private lateinit var mContentTextView: TextView

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

        mTitleTextView = view.findViewById(R.id.tv_title)
        mContentTextView = view.findViewById(R.id.tv_content)
        mCloseImageView = view.findViewById(R.id.iv_close)

        initListener()

        val title = arguments?.getString(TITLE,) ?: resources.getString(R.string.license_server_protocol)
        val content = arguments?.getString(CONTENT) ?: resources.getString(R.string.license_server_content)
        mTitleTextView.text = title
        mContentTextView.text = content
    }

    private fun initListener(){
        mCloseImageView.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}