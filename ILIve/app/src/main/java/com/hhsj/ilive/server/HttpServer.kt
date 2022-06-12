package com.hhsj.ilive.server

import com.hhsj.ilive.data.HttpResponse
import com.hhsj.ilive.data.UserInfo
import com.hhsj.ilive.utils.LogUtils
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val HOST = "http://47.110.153.16:30238"

object HttpServer {

    private val retrofit: ServerAPI = Retrofit.Builder()
        .baseUrl(HOST)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ServerAPI::class.java)

    fun getVerifyCode(phone: String,success: () -> Unit,failure: (String?) -> Unit){
        retrofit.getVertifyCode(phone).enqueue(object: Callback<HttpResponse>{
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
                success.invoke()
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                failure.invoke(t.message)
            }
        })
    }

    fun registerOrLogin(phone: String,code: String,success: (UserInfo) -> Unit,failure: (String?) -> Unit){
        val body = "{\"appPhone\":$phone,\"code\":$code}"
        val requestBody = RequestBody.create(MediaType.parse("application/json"),body)
        retrofit.registerOrLogin(requestBody).enqueue(object: Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val userInfo = response.body()
                LogUtils.e("registerOrLogin : $userInfo")
                if(userInfo != null){
                    val userInfoCode = userInfo.code
                    if(userInfoCode != 200){
                        failure(userInfo.msg)
                    }else{
                        success(userInfo)
                    }
                }else{
                    failure("登录失败")
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                failure.invoke(t.message)
            }
        })
    }

    fun logout(token: String,success: () -> Unit,failure: () -> Unit){
        retrofit.logOut(token).enqueue(object: Callback<HttpResponse>{
            override fun onResponse(call: Call<HttpResponse>, response: Response<HttpResponse>) {
            }

            override fun onFailure(call: Call<HttpResponse>, t: Throwable) {
                failure.invoke()
            }
        })
    }
}