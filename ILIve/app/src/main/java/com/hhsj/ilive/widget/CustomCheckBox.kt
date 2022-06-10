package com.hhsj.ilive.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.hhsj.ilive.R

class CustomCheckBox(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private var mIsChecked: Boolean = false
    private var onCheckedListener: ((isChecked: Boolean) -> Unit)? = null

    init{
        setBackground()
        setOnClickListener {
            mIsChecked = !mIsChecked
            setBackground()
            onCheckedListener?.invoke(mIsChecked)
        }
    }

    private fun setBackground(){
        background = if (mIsChecked) {
            ResourcesCompat.getDrawable(
                context.resources,
                R.mipmap.icon_checkbox_checked,
                null
            )
        }else{
            ResourcesCompat.getDrawable(
                context.resources,
                R.mipmap.icon_checkbox_unchecked,
                null
            )
        }
    }

    fun setChecked(checked: Boolean){
        mIsChecked = checked
        setBackground()
    }

    fun onCheckedChangeListener(onChecked: (isChecked: Boolean) -> Unit) {
        this.onCheckedListener = onChecked
    }
}