package com.hhsj.ilive.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hhsj.ilive.ui.main.*

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 5

    override fun createFragment(position: Int) = when(position){
        0 -> MessageFragment()
        1 -> AddressBookFragment()
        2 -> ILiveFragment()
        3 -> ServiceFragment()
        4 -> MyFragment()
        else -> MessageFragment()
    }
}