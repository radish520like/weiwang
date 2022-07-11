package com.hhsj.ilive

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hhsj.ilive.ui.main.ConfirmFragment
import com.hhsj.ilive.widget.CustomToast
import org.devio.takephoto.app.TakePhotoFragment

const val VERIFY_CODE_FROM_KEY = "verify_code_from_key"
const val UPDATE_NEW_PHONE = "update_new_phone"
const val VERIFY_CODE_FROM_UPDATE_PHONE_NEW = "verify_code_from_update_phone_new"
const val VERIFY_CODE_FROM_UPDATE_PHONE_OLD = "verify_code_from_update_phone_old"
const val VERIFY_CODE_FROM_LOGOUT = "verify_code_from_logout"

open class BaseFragment : TakePhotoFragment() {

    var mScreenWidthPixels: Int = 0
    var mScreenHeightPixels: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val displayMetrics = context.resources.displayMetrics
        mScreenWidthPixels = displayMetrics.widthPixels
        mScreenHeightPixels = displayMetrics.heightPixels
    }

    fun goBack(nacController: NavController) {
        nacController.popBackStack()
    }

    fun initConfirmFragmentBundle(
        title: String,
        @DrawableRes picture: Int,
        tips: String,
        bottom1: String,
        bottom2: String,
        confirmType: ConfirmFragment.Companion.ConfirmType
    ): Bundle {
        val bundle = Bundle()
        bundle.putString(
            ConfirmFragment.TITLE,
            title
        )
        bundle.putInt(ConfirmFragment.ICON, picture)
        bundle.putString(
            ConfirmFragment.TIPS,
            tips
        )
        bundle.putString(
            ConfirmFragment.BOTTOM_ITEM1,
            bottom1
        )
        bundle.putString(
            ConfirmFragment.BOTTOM_ITEM2,
            bottom2
        )
        bundle.putSerializable(
            ConfirmFragment.TYPE,
            confirmType
        )
        return bundle
    }

    fun hideSoft() {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    fun hideSoftWithEditText(editText: EditText) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun showSoft(editText: EditText) {
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

    fun margin(
        targetView: View,
        marginStart: Float = 0.0f,
        marginTop: Float = 0.0f,
        marginEnd: Float = 0.0f,
        marginBottom: Float = 0.0f
    ) {
        val layoutParams = targetView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.leftMargin = (marginStart / 100.0f * mScreenWidthPixels).toInt()
        layoutParams.topMargin = (marginTop / 100.0f * mScreenWidthPixels).toInt()
        layoutParams.rightMargin = (marginEnd / 100.0f * mScreenWidthPixels).toInt()
        layoutParams.bottomMargin = (marginBottom / 100.0f * mScreenWidthPixels).toInt()
        targetView.layoutParams = layoutParams
    }

    fun calculateViewHeight(targetView: View, height: Float) {
        val layoutParams = targetView.layoutParams
        layoutParams.height = (height / 100.0f * mScreenWidthPixels).toInt()
        targetView.layoutParams = layoutParams
        targetView.invalidate()
    }

    fun calculateViewWidth(targetView: View, width: Float) {
        val layoutParams = targetView.layoutParams
        layoutParams.width = (width / 100.0f * mScreenWidthPixels).toInt()
        targetView.layoutParams = layoutParams
        targetView.invalidate()
    }

    fun calculateView(targetView: View, width: Float, height: Float) {
        val layoutParams = targetView.layoutParams
        layoutParams.width = (width / 100.0f * mScreenWidthPixels).toInt()
        layoutParams.height = (height / 100.0f * mScreenWidthPixels).toInt()
        targetView.layoutParams = layoutParams
        targetView.invalidate()
    }

    fun getRealWidth(width: Float) = (width / 100.0f * mScreenWidthPixels).toInt()
    fun getRealHeight(height: Float) = (height / 100.0f * mScreenWidthPixels).toInt()

    fun checkPhone(phone: String) =
        (phone.length != 11 || phone[0] != '1' || phone.toLongOrNull() == null)

    fun loadUrlWithBitmap(url: String, imageView: ImageView, block: (bitmap: Bitmap?) -> Unit) {
        Glide.with(requireContext()).asBitmap().load(url).addListener(object :
            RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                CustomToast.getInstance(requireContext()).show("加载图片失败")
                return true
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                block(resource)
                imageView.setImageBitmap(resource)
                return true
            }
        }).submit()
    }
}