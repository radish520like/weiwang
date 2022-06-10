package com.hhsj.ilive.repository

import com.hhsj.ilive.utils.SPUtils

private const val USER_PHONE = "user_phone"

class UserInfoRepository {
    fun getPhone() = SPUtils.getString(USER_PHONE)
}