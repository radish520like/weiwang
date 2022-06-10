package com.hhsj.ilive.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.hhsj.ilive.R

class CustomMyInfoItemView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private lateinit var mTitleTextView: TextView
    private lateinit var mValueTextView: TextView

    private var mRootInflateView: View =
        LayoutInflater.from(context).inflate(R.layout.view_my_info_item,this,true)
    private var mTitle: String
    private var mValue: String

    init{
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.CustomMyInfoItemView)
        mTitle = obtainStyledAttributes.getString(R.styleable.CustomMyInfoItemView_title)?: ""
        mValue = obtainStyledAttributes.getString(R.styleable.CustomMyInfoItemView_value)?: ""
        obtainStyledAttributes.recycle()

        initView()
    }

    private fun initView(){
        mTitleTextView = mRootInflateView.findViewById(R.id.tv_title)
        mValueTextView = mRootInflateView.findViewById(R.id.tv_value)

        mTitleTextView.text = mTitle
        mValueTextView.text = mValue
    }

    public fun setValue(str: String){
        mValueTextView.text = str
    }
}