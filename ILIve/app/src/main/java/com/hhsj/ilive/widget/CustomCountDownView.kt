package com.hhsj.ilive.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import com.hhsj.ilive.utils.dp

class CustomCountDownView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private lateinit var mRectF: RectF
    private var mRadius: Float = 11.dp
    private var mStrokeColor: String = "#E2E2E2"
    private var mStrokeWidth: Float = 1.dp
    private var mFinishColor: String = "#2A82E4"
    private var mCurrentCountDownNum: Long = 0
    private var mCountDownFuture: Long = 0
    private var mCountDownInterval: Long = 0
    private var mInCountDown = false
    private var mIsFinished: Boolean = false

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val mCountDownTimer by lazy {
        object : CountDownTimer(mCountDownFuture, mCountDownInterval) {
            override fun onTick(p0: Long) {
                mCurrentCountDownNum = p0
                invalidate()
            }

            override fun onFinish() {
                mCurrentCountDownNum = 0
                mInCountDown = false
                invalidate()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!mIsFinished) {
            //draw circle ring
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeWidth = mStrokeWidth
            mPaint.color = Color.parseColor(mStrokeColor)
            canvas.drawCircle(width / 2f, height / 2f, mRadius, mPaint)

            //draw arc
            mPaint.style = Paint.Style.FILL
            canvas.drawArc(
                mRectF,
                0f,
                360 * (mCurrentCountDownNum.toFloat() / mCountDownFuture),
                true,
                mPaint
            )
        }else{
            //draw circle
            mPaint.style = Paint.Style.FILL
            mPaint.color = Color.parseColor(mFinishColor)
            canvas.drawCircle(width / 2f, height / 2f, mRadius - 3.dp, mPaint)
        }

    }

    fun setOptions(
        radius: Float = 11.dp,
        strokeColor: String = "#E2E2E2",
        strokeWidth: Float = 1.dp,
        finishColor: String = "#2A82E4"
    ) {
        this.mRadius = radius
        this.mStrokeColor = strokeColor
        this.mStrokeWidth = strokeWidth
        this.mFinishColor = finishColor
    }

    fun setFinished(isFinished: Boolean) {
        this.mIsFinished = isFinished
        invalidate()
    }

    fun startCountDown(countDownNum: Long, interval: Long) {
        if (!mInCountDown) {
            mCountDownFuture = countDownNum
            mCountDownInterval = interval
            mCountDownTimer.start()
        }
        mInCountDown = true
    }
}