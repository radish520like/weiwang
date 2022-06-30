package com.hhsj.ilive.ui.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hhsj.ilive.BaseActivity
import com.hhsj.ilive.R
import com.hhsj.ilive.adapter.MainViewPagerAdapter

class MainActivity : BaseActivity() {

    private lateinit var mViewPager: ViewPager2
    private val mTabTitleList = mutableListOf<String>()
    private val mTabIconList = mutableListOf<Drawable?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initViewPager()
        initTabLayout()
    }

    private fun initData() {
        mTabTitleList.add(resources.getString(R.string.tab_tag_message))
        mTabTitleList.add(resources.getString(R.string.tab_tag_address_book))
        mTabTitleList.add(resources.getString(R.string.tab_tag_ilive))
        mTabTitleList.add(resources.getString(R.string.tab_tag_service))
        mTabTitleList.add(resources.getString(R.string.tab_tag_my))

        mTabIconList.add(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.selector_icon_home_tab_message,
                resources.newTheme()
            )
        )
        mTabIconList.add(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.selector_icon_home_tab_addressbook,
                resources.newTheme()
            )
        )
        mTabIconList.add(
            ResourcesCompat.getDrawable(
                resources,
                R.mipmap.icon_home_tab_message,
                resources.newTheme()
            )
        )
        mTabIconList.add(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.selector_icon_home_tab_server,
                resources.newTheme()
            )
        )
        mTabIconList.add(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.selector_icon_home_tab_my,
                resources.newTheme()
            )
        )
    }

    private fun initViewPager() {
        mViewPager = findViewById(R.id.view_pager)
        val mMainViewPagerAdapter = MainViewPagerAdapter(this)
        mViewPager.adapter = mMainViewPagerAdapter
    }

    private fun initTabLayout() {
        val mTabLayout: TabLayout = findViewById(R.id.tab_layout)
        val tabLayoutMediator = TabLayoutMediator(
            mTabLayout, mViewPager
        ) { tab, position ->
            tab.customView = getTab(position)
        }

        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                onTabSelectedChange(tab.customView!!, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                onTabSelectedChange(tab.customView!!, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        tabLayoutMediator.attach()
    }

    private fun getTab(position: Int): View {
        val inflate = LayoutInflater.from(this).inflate(R.layout.layout_home_tab_item, null)
        val textView: TextView = inflate.findViewById(R.id.tv_title)
        val imageView: ImageView = inflate.findViewById(R.id.iv_icon)

        textView.text = mTabTitleList[position]
        imageView.setImageDrawable(mTabIconList[position])
        return inflate
    }

    private fun onTabSelectedChange(view: View, isSelected: Boolean) {
        val textView: TextView = view.findViewById(R.id.tv_title)
        if (isSelected) {
            textView.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.round_rect_button_enable,
                    resources.newTheme()
                )
            )
        } else {
            textView.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.black_80,
                    resources.newTheme()
                )
            )
        }
    }
}