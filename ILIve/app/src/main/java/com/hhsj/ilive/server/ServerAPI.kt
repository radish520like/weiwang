package com.hhsj.ilive.server

import com.hhsj.ilive.data.HttpResponse
import com.hhsj.ilive.data.UserInfo
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ServerAPI {

    @GET("/api/oauth2/app/sms/sendCode")
    fun getVerifyCode(@Query("phone") phone: String): Call<HttpResponse>

    @POST("/api/oauth2/app/loginAndRegister")
    fun registerOrLogin(@Body body: RequestBody): Call<UserInfo>

    @GET("/api/member/member/loginOut")
    fun logOut(@Query("Authorization") bearerToken: String): Call<HttpResponse>

    @POST("/api/member/member/update/info")
    fun updateUserInfo(
        @Query("Authorization") bearerToken: String,
        @Body body: RequestBody
    ): Call<HttpResponse>

    @GET("/api/member/member/info")
    fun getUserInfo(@Query("Authorization") bearerToken: String): Call<UserInfo>

    @GET("/api/member/member/sms/sendCode")
    fun getVerifyCode(@Query("Authorization") bearerToken: String,@Query("phone") phone: String): Call<HttpResponse>

    @GET("/api/member/member/check/phone")
    fun checkPhone(
        @Query("Authorization") bearerToken: String,
        @Query("phone") phone: String, @Query("code") code: String
    ): Call<HttpResponse>
}