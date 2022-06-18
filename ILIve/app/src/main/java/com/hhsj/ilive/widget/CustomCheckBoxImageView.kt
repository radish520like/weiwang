package com.hhsj.ilive.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.hhsj.ilive.R

class CustomCheckBoxImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    var mIsChecked: Boolean = false
        set(value) {
            field = value
            setCustomBackground()
        }

    private var onCheckedListener: ((isChecked: Boolean) -> Unit)? = null

    init {
        setCustomBackground()
        setOnClickListener {
            mIsChecked = !mIsChecked
            setCustomBackground()
            onCheckedListener?.invoke(mIsChecked)
        }
    }

    private fun setCustomBackground() {
        val drawable = if (mIsChecked) {
            ResourcesCompat.getDrawable(
                context.resources,
                R.mipmap.icon_checkbox_checked,
                null
            )
        } else {
            ResourcesCompat.getDrawable(
                context.resources,
                R.mipmap.icon_checkbox_unchecked,
                null
            )
        }
        setImageDrawable(drawable)
        invalidate()
    }

    fun onCheckedChangeListener(onChecked: (isChecked: Boolean) -> Unit) {
        this.onCheckedListener = onChecked
    }
}