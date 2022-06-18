package com.hhsj.ilive.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.renderscript.RSRuntimeException
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.charset.Charset
import java.security.MessageDigest

import jp.wasabeef.glide.transformations.internal.FastBlur;
import jp.wasabeef.glide.transformations.internal.RSBlur;

private const val STRING_CHARSET_NAME = "UTF-8"
private const val ID = "com.kevin.glidetest.BlurTransformation"
private val CHARSET = Charset.forName(STRING_CHARSET_NAME)
private val ID_BYTES = ID.toByte()
private const val MAX_RADIUS = 25
private const val DEFAULT_DOWN_SAMPLING = 1


private class BlurTransformation(
    private val mContext: Context,
    private val mBitmapPool: BitmapPool,
    private val mRadius: Int,
    private val mSampling: Int
) : BitmapTransformation() {

//    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val source: Bitmap = toTransform
        val width = source.width
        val height = source.height
        val scaledWidth = width / mSampling
        val scaledHeight = height / mSampling

        var bitmap = mBitmapPool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
//        if (bitmap == null) {
//            bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
//        }

        val canvas = Canvas(bitmap)
        canvas.scale((1 / mSampling).toFloat(), (1 / mSampling).toFloat())
        val paint = Paint(Paint.FILTER_BITMAP_FLAG)

        canvas.drawBitmap(source, 0f, 0f, paint)

        bitmap =
            try {
                RSBlur.blur(mContext, bitmap, mRadius);
            } catch (e: RSRuntimeException) {
                FastBlur.blur(bitmap, mRadius, true);
            }
        return bitmap
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is BlurTransformation
    }
}