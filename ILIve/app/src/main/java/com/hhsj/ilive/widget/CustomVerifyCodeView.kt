package com.hhsj.ilive.widget

import android.content.Context
import android.os.CountDownTimer
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.internal.TextWatcherAdapter
import com.hhsj.ilive.R
import java.lang.StringBuilder

class CustomVerifyCodeView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private var mRootViewConstraintLayout: ConstraintLayout
    private var mVerifyCode1TextView: TextView
    private var mVerifyCode2TextView: TextView
    private var mVerifyCode3TextView: TextView
    private var mVerifyCode4TextView: TextView
    private var mVertifyCodeEditText: EditText
    private var mLine1: View
    private var mLine2: View
    private var mLine3: View
    private var mLine4: View
    private val mTextViewList = mutableListOf<TextView>()
    private val mLineViewList = mutableListOf<View>()
    private val mStringBuilder = StringBuilder()
    private var mCurrentLineView: View? = null
    private val mCountDownTimer by lazy {
        object : CountDownTimer(Long.MAX_VALUE, 200) {
            override fun onTick(value: Long) {
                if(mCurrentLineView != null){
                    if(value % 2 == 0L){
                        mCurrentLineView?.visibility = View.VISIBLE
                    }else{
                        mCurrentLineView?.visibility = View.GONE
                    }
                }
            }

            override fun onFinish() {}
        }
    }

    private lateinit var mOnCompletion: (msg: String) -> Unit


    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.verify_code_layout, this, true)
        mRootViewConstraintLayout = rootView.findViewById(R.id.root_view)
        mVerifyCode1TextView = rootView.findViewById(R.id.tv_1)
        mVerifyCode2TextView = rootView.findViewById(R.id.tv_2)
        mVerifyCode3TextView = rootView.findViewById(R.id.tv_3)
        mVerifyCode4TextView = rootView.findViewById(R.id.tv_4)

        mLine1 = rootView.findViewById(R.id.line_1)
        mLine2 = rootView.findViewById(R.id.line_2)
        mLine3 = rootView.findViewById(R.id.line_3)
        mLine4 = rootView.findViewById(R.id.line_4)

        mVertifyCodeEditText = rootView.findViewById(R.id.et_verify_code)

        mTextViewList.add(mVerifyCode1TextView)
        mTextViewList.add(mVerifyCode2TextView)
        mTextViewList.add(mVerifyCode3TextView)
        mTextViewList.add(mVerifyCode4TextView)

        mLineViewList.add(mLine1)
        mLineViewList.add(mLine2)
        mLineViewList.add(mLine3)
        mLineViewList.add(mLine4)

        mCurrentLineView = mLine1
        mCountDownTimer.start()
        initListener()
    }

    private fun initListener() {
        mVertifyCodeEditText.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                mTextViewList.withIndex().forEach{
                    if(it.index >= s.length){
                        mTextViewList[it.index].text = ""
                    }else{
                        mTextViewList[it.index].text = s[it.index].toString()
                    }
                }
                if (s.length == mTextViewList.size && ::mOnCompletion.isInitialized) {
                    mStringBuilder.clear()
                    mTextViewList.forEach {
                        mStringBuilder.append(it.text)
                    }
                    mOnCompletion.invoke(mStringBuilder.toString())
                }
                mCurrentLineView?.visibility = View.GONE
                val length = if(s.isNotEmpty() && s.length < mLineViewList.size){
                    s.length
                }else if(s.length - 1 < 0 || s.isEmpty()){
                    0
                }else{
                    s.length - 1
                }
                mCurrentLineView = mLineViewList[length]
            }
        })
    }

    fun release(){
        mCountDownTimer.cancel()
    }

    fun clear(){
        mVertifyCodeEditText.setText("")
    }

    fun setOnCompletionListener(block: (msg: String) -> Unit) {
        mOnCompletion = block
    }
}