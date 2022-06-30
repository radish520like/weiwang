package com.hhsj.ilive

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.navigation.NavController

const val VERIFY_CODE_FROM_KEY = "verify_code_from_key"
const val VERIFY_CODE_FROM_UPDATE_PHONE_NEW = "verify_code_from_update_phone_new"
const val VERIFY_CODE_FROM_UPDATE_PHONE_OLD = "verify_code_from_update_phone_old"
const val VERIFY_CODE_FROM_LOGOUT = "verify_code_from_logout"

open class BaseFragment : Fragment() {

    private var mScreenWidthPixels: Int = 0
    private var mScreenHeightPixels: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val displayMetrics = context.resources.displayMetrics
        mScreenWidthPixels = displayMetrics.widthPixels
        mScreenHeightPixels = displayMetrics.heightPixels
    }

    fun goBack(nacController: NavController){
        nacController.popBackStack()
    }

    fun hideSoft() {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    fun hideSoftWithEditText(editText: EditText){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun showSoft(editText: EditText){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun margin(
        rootView: ConstraintLayout,
        targetView: View,
        startIntSide: Int,
        coordinate: View,
        endIntSide: Int,
        marginPercent: Float
    ) {

        //1.创建 constraintSet
        val constraintSet = ConstraintSet()
        constraintSet.apply {
            //2.clone 根布局
            clone(rootView)
            //3.设置布局
            /*
                1.要修改的 View id
                2.要修改的 View 的方向
                3.对应目标 View 的id
                4.对应目标 View 的方向
                5.Margin
           */
            connect(
                targetView.id, startIntSide,
                coordinate.id, endIntSide, (marginPercent / 100.0f * mScreenWidthPixels).toInt()
            )
            //4.apply 应用
            applyTo(rootView)
        }
    }
}