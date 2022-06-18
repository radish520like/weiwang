package com.hhsj.ilive.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.hhsj.ilive.R
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

class CustomEnableTextView(context: Context, attrs: AttributeSet) :
    CustomFontTextView(context, attrs) {

    init{
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView)
        val fontIndex = obtainStyledAttributes.getInt(R.styleable.CustomFontTextView_textFont,6)
        obtainStyledAttributes.recycle()

        var path = ""
        path = when(fontIndex){
            0 -> "fonts/MiSans-Bold.ttf"
            1 -> "fonts/MiSans-Demibold.ttf"
            2 -> "fonts/MiSans-ExtraLight.ttf"
            3 -> "fonts/MiSans-Heavy.ttf"
            4 -> "fonts/MiSans-Light.ttf"
            5 -> "fonts/MiSans-Medium.ttf"
            6 -> "fonts/MiSans-Normal.ttf"
            7 -> "fonts/MiSans-Regular.ttf"
            8 -> "fonts/MiSans-Semibold.ttf"
            9 -> "fonts/MiSans-Thin.ttf"
            else -> "fonts/MiSans-Normal.ttf"
        }
        val typeFace = Typeface.createFromAsset(context.assets, path)
        typeface = typeFace
    }

    fun canClick(enable: Boolean){
//        background = if(enable){
//            ResourcesCompat.getDrawable(resources,R.drawable.shape_round_rect_button_enable,resources.newTheme())
//        }else{
//            ResourcesCompat.getDrawable(resources,R.drawable.shape_round_rect_button_no_enable,resources.newTheme())
//        }
        background =ResourcesCompat.getDrawable(resources,R.drawable.shape_round_rect_button_enable,resources.newTheme())
    }
}