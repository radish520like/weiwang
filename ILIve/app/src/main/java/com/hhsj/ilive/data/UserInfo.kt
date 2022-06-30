package com.hhsj.ilive.data

data class UserInfo(
    val code: Int,
    val `data`: UserInfoData,
    val msg: String,
    val token: String
)

data class UserInfoData(
    val account: String,
    val cardNo: String,
    val createTime: String,
    val header: String,
    val id: Int,
    val nickName: String,
    val realName: String,
    val phone: String,
    val status: Int,
    val updateTime: String,
    val nickNameLimit: Int,
    val accountLimit: Int
)