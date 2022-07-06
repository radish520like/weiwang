package com.hhsj.ilive.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.hhsj.ilive.R

class CustomFontEditText(context: Context, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {

    init{
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.CustomFontEditText)
        val fontIndex = obtainStyledAttributes.getInt(R.styleable.CustomFontEditText_editFont,6)
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

        val hint = hint.toString()
        setOnFocusChangeListener { _, focus ->
            if(focus){
                setHint("")
            }else{
                setHint(hint)
            }
        }
    }
}