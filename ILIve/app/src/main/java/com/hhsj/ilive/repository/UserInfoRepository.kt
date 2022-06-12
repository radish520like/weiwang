package com.hhsj.ilive.repository

import com.hhsj.ilive.data.UserInfo
import com.hhsj.ilive.utils.SPUtils

private const val USER_PHONE = "user_phone"
private const val USER_TOKEN = "user_token"
private const val USER_NICKNAME = "user_nickName"
private const val USER_HEADER = "user_header"
private const val USER_ACCOUNT = "user_account"

class UserInfoRepository {
    fun getPhone() = SPUtils.getString(USER_PHONE)
    fun getToken() = SPUtils.getString(USER_TOKEN)
    fun setToken(token: String) = SPUtils.putString(USER_TOKEN,token)
    fun getNickName() = SPUtils.getString(USER_NICKNAME)
    fun getHeader() = SPUtils.getString(USER_HEADER)

    fun saveUserInfo(info: UserInfo) {
        SPUtils.putString(USER_TOKEN,info.token)
        SPUtils.putString(USER_PHONE,info.data.phone)
        SPUtils.putString(USER_NICKNAME,info.data.nickName)
        SPUtils.putString(USER_HEADER,info.data.header)
        SPUtils.putString(USER_ACCOUNT,info.data.account)
    }


}