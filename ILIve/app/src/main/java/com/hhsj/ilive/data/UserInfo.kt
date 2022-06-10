package com.hhsj.ilive.data

data class UserInfo(
    val code: Int,
    val `data`: Data,
    val msg: String,
    val token: String
)

data class Data(
    val account: String,
    val cardNo: String,
    val createTime: String,
    val header: String,
    val id: Int,
    val nickName: String,
    val phone: String,
    val status: Int,
    val updateTime: String
)