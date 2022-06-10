package com.hhsj.ilive.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.BaseFragment

/**
 * 服务界面
 * @author YuHan
 */
class ServiceFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

}