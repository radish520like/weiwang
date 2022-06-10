package com.hhsj.ilive.widget

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.hhsj.ilive.R

class LogoutDialogFragment: DialogFragment() {

    private var mCancelListener: (() -> Unit)? = null
    private var mOnLogOutListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val window = dialog?.window
        window?.attributes?.apply {
            gravity = Gravity.BOTTOM
            windowAnimations = R.style.BottomDialogAnimation
            window.attributes = this
        }
        window?.setBackgroundDrawable(ColorDrawable())
        return inflater.inflate(R.layout.layout_logout_dialog_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mLogoutTextView: TextView = view.findViewById(R.id.tv_logout)
        val mCancelTextView: TextView = view.findViewById(R.id.tv_cancel)

        mCancelTextView.setOnClickListener {
            println("abc : aaaaa")
            mCancelListener?.invoke()
        }

        mLogoutTextView.setOnClickListener {
            println("abc : bbbbb")
            mOnLogOutListener?.invoke()
        }
    }

    override fun onResume() {
        super.onResume()
        val window = dialog?.window
        window?.attributes?.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes = this
        }
    }

    public fun setOnCancelListener(block: ()-> Unit){
        mCancelListener = block
    }

    public fun setOnLogoutListener(block: ()-> Unit){
        mOnLogOutListener = block
    }
}