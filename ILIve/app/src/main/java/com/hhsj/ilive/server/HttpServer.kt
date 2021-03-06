package com.hhsj.ilive.server

import android.util.Log
import com.hhsj.ilive.data.HttpResponse
import com.hhsj.ilive.data.UserInfo
import com.hhsj.ilive.utils.LogUtils
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.StringBuilder

//private const val HOST = "http://47.110.153.16:30238"
private const val HOST = "http://219.246.116.9:88"
private const val TAG = "HttpServer"

object HttpServer {

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.e(
            TAG,
            "retrofit Log : $message"
        )
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: ServerAPI = Retrofit.Builder()
        .baseUrl(HOST)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ServerAPI::class.java)

    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * 注册登录验证码
     */
    fun getVerifyCode(phone: String, success: () -> Unit, failure: (String?) -> Unit) {
        retrofit.getVerifyCode(phone).enqueue(object : Callback<HttpResponse> {
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                success.invoke()
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                failure.invoke(t.message)
            }
        })
    }

    /**
     * 非注册登录验证码
     * phone: 非必须，校验旧手机号时可不传，校验新手机号必传
     */
    fun getVerifyCode(token: String,phone: String = "", success: () -> Unit, failure: (String?) -> Unit) {
        retrofit.getVerifyCode(token,phone).enqueue(object : Callback<HttpResponse> {
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                success.invoke()
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                failure.invoke(t.message)
            }
        })
    }

    fun registerOrLogin(
        phone: String,
        code: String,
        success: (UserInfo) -> Unit,
        failure: (String?) -> Unit
    ) {
        val body = "{\"appPhone\":$phone,\"code\":$code}"
        val requestBody = RequestBody.create(MediaType.parse("application/json"), body)
        retrofit.registerOrLogin(requestBody).enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val userInfo = response.body()
                LogUtils.e("registerOrLogin : $userInfo")
                if (userInfo != null) {
                    val userInfoCode = userInfo.code
                    if (userInfoCode != 200) {
                        failure(userInfo.msg)
                    } else {
                        success(userInfo)
                    }
                } else {
                    failure("登录失败")
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                failure.invoke(t.message)
            }
        })
    }

    fun logout(token: String, success: () -> Unit, failure: () -> Unit) {
        retrofit.logOut(token).enqueue(object : Callback<HttpResponse> {
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                failure.invoke()
            }
        })
    }

    fun getUserInfo(token: String, success: (UserInfo) -> Unit, failure: (String) -> Unit) {
        retrofit.getUserInfo(token).enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val userInfo = response.body()
                if (userInfo != null) {
                    val userInfoCode = userInfo.code
                    if (userInfoCode != 200) {
                        failure(userInfo.msg)
                    } else {
                        success(userInfo)
                    }
                } else {
                    failure(response.message())
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                failure.invoke(t.message!!)
            }
        })
    }

    fun checkPhone(
        token: String,
        phone: String = "",
        code: String,
        success: () -> Unit,
        failure: (String) -> Unit
    ) {
        retrofit.checkPhone(token,phone,code).enqueue(object: Callback<HttpResponse>{
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                success.invoke()
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                t.message?.apply {
                    failure.invoke(this)
                }
            }
        })
    }

    fun updateUserInfo(
        token: String,
        nickName: String = "",
        account: String = "",
        header: String = "",
        phone: String = "",
        success: () -> Unit,
        failure: (String?) -> Unit
    ) {
        val stringBuilder = StringBuilder("{")
            .apply {
                if (nickName.isNotEmpty()) append("\"nickName\":\"$nickName\"")
                if (account.isNotEmpty()) append("\"account\":\"$account\"")
                if (header.isNotEmpty()) append("\"header\":\"$header\"")
                if (phone.isNotEmpty()) append("\"phone\":\"$phone\"")
                append("}")
            }
        val requestBody =
            RequestBody.create(MediaType.parse("application/json"), stringBuilder.toString())
        retrofit.updateUserInfo(token, requestBody).enqueue(object: Callback<HttpResponse>{
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                var body = response.body()
                body?.apply {
                    if(this.code == 200){
                        success()
                    }else{
                        failure(this.msg)
                    }
                }
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                failure(t.message)
            }
        })
    }
}