package com.hhsj.ilive.repository

import com.hhsj.ilive.data.UserInfo
import com.hhsj.ilive.utils.SPUtils

private const val USER_ID = "user_id"
private const val USER_PHONE = "user_phone"
private const val USER_TOKEN = "user_token"
private const val USER_NICKNAME = "user_nickName"
private const val USER_HEADER = "user_header"
private const val USER_ACCOUNT = "user_account"
private const val USER_NICK_NAME_LIMIT = "user_nick_name_limit"
private const val USER_ACCOUNT_LIMIT = "user_account_limit"

class UserInfoRepository {
    fun getPhone() = SPUtils.getString(USER_PHONE)
    fun getToken() = SPUtils.getString(USER_TOKEN)
    fun getNickName() = SPUtils.getString(USER_NICKNAME)
    fun getHeader() = SPUtils.getString(USER_HEADER)
    fun getAccount() = SPUtils.getString(USER_ACCOUNT)
    fun getNickNameLimit() = SPUtils.getInt(USER_NICK_NAME_LIMIT)
    fun getAccountLimit() = SPUtils.getInt(USER_ACCOUNT_LIMIT)
    fun getUserID() = SPUtils.getInt(USER_ID)

    fun setToken(token: String) = SPUtils.putString(USER_TOKEN,token)
    fun setPhone(phone: String) = SPUtils.putString(USER_PHONE,phone)
    fun setNickName(nickName: String) = SPUtils.putString(USER_NICKNAME,nickName)
    fun setHeader(header: String) = SPUtils.putString(USER_HEADER,header)
    fun setAccount(account: String) = SPUtils.putString(USER_ACCOUNT,account)

    fun saveUserInfo(info: UserInfo) {
        SPUtils.putInt(USER_ID,info.data.id)
        SPUtils.putString(USER_TOKEN,"bearer ${info.token}")
        SPUtils.putString(USER_PHONE,info.data.phone)
        SPUtils.putString(USER_NICKNAME,info.data.nickName)
        SPUtils.putString(USER_HEADER,info.data.header)
        SPUtils.putString(USER_ACCOUNT,info.data.account)
        SPUtils.putInt(USER_NICK_NAME_LIMIT,info.data.nickNameLimit)
        SPUtils.putInt(USER_ACCOUNT_LIMIT,info.data.accountLimit)
    }

    fun saveUserInfoWithoutToken(info: UserInfo){
        SPUtils.putInt(USER_ID,info.data.id)
        SPUtils.putString(USER_PHONE,info.data.phone)
        SPUtils.putString(USER_NICKNAME,info.data.nickName)
        SPUtils.putString(USER_HEADER,info.data.header)
        SPUtils.putString(USER_ACCOUNT,info.data.account)
        SPUtils.putInt(USER_NICK_NAME_LIMIT,info.data.nickNameLimit)
        SPUtils.putInt(USER_ACCOUNT_LIMIT,info.data.accountLimit)
    }
}