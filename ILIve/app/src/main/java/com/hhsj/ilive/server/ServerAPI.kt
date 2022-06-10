package com.hhsj.ilive.server

import com.hhsj.ilive.data.HttpResponse
import com.hhsj.ilive.data.UserInfo
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ServerAPI {

    @GET("/api/oauth2/app/sms/sendCode")
    fun getVertifyCode(@Query("phone") phone: String): Call<HttpResponse>

    @POST("/api/oauth2/app/loginAndRegister")
    fun registerOrLogin(@Body body: RequestBody): Call<UserInfo>

    @GET("/api/member/member/loginOut")
    fun logOut(@Query("Authorization") bearerToken: String): Call<HttpResponse>
}