package com.hhsj.ilive.widget

import android.graphics.*
import android.graphics.drawable.Drawable
import com.hhsj.ilive.utils.dp

class ShapeCircleDrawable : Drawable() {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun draw(canvas: Canvas) {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 1.dp
        mPaint.color = Color.parseColor("#A2A2A2")
        canvas.drawCircle(
            (bounds.right - bounds.left) / 2f,
            (bounds.bottom - bounds.top) / 2f,
            11.dp,
            mPaint
        )
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.parseColor("#2A82E4")
        canvas.drawCircle(
            (bounds.right - bounds.left) / 2f,
            (bounds.bottom - bounds.top) / 2f,
            8.dp, mPaint
        )
    }

    override fun setAlpha(p0: Int) {
        mPaint.alpha = p0
    }

    override fun setColorFilter(p0: ColorFilter?) {
        mPaint.colorFilter = p0
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE
}