package com.hhsj.ilive.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hhsj.ilive.R
import com.hhsj.ilive.ui.BaseFragment

/**
 * 通讯录
 * @author YuHan
 */
class AddressBookFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_address_book, container, false)
    }
}