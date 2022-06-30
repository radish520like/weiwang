package com.hhsj.ilive.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hhsj.ilive.BaseFragment
import com.hhsj.ilive.R

/**
 * 消息界面
 * @author YuHan
 */
class MessageFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

}