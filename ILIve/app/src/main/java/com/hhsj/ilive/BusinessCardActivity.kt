package com.hhsj.ilive

import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

class BusinessCardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_card)

        val imageView: ImageView = findViewById(R.id.iv)

        Glide.with(this)
            .load("https://micronet-link.oss-cn-hangzhou.aliyuncs.com/2022-06-01/header_5.png")
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 8)))
            .into(imageView)
    }
}