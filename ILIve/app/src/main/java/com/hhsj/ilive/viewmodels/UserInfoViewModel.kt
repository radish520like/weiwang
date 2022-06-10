package com.hhsj.ilive.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hhsj.ilive.data.UserInfo
import com.hhsj.ilive.server.HttpServer
import com.hhsj.ilive.utils.SPUtils

class UserInfoViewModel: ViewModel() {

    var phoneNumber: MutableLiveData<String> = MutableLiveData()
    var readChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    private var userInfo: MutableLiveData<UserInfo> = MutableLiveData()

    fun setUserInfo(info: UserInfo){
        userInfo.value = info
        SPUtils.putString("token",info.token)
        SPUtils.putString("phone",info.data.phone)
        SPUtils.putString("nickName",info.data.nickName)
        SPUtils.putString("header",info.data.header)
        SPUtils.putString("account",info.data.account)
    }

    fun getToken() = SPUtils.getString("token")
    fun getPhone() = phoneNumber.value?: SPUtils.getString("phone")
    fun getHeader() = SPUtils.getString("header")
    fun getNickName() = SPUtils.getString("nickName")

    fun getVerifyCode(success: () -> Unit,failure: (String?) -> Unit){
        HttpServer.getVerifyCode(phoneNumber.value?: getPhone(),success,failure)
    }

    fun registerOrLogin(phone: String,code: String,success: (UserInfo) -> Unit,failure: (String?) -> Unit){
        HttpServer.registerOrLogin(phone,code,{
            setUserInfo(it)
            success.invoke(it)
        },failure)
    }

    fun logout(success: () -> Unit,failure: () -> Unit){
        HttpServer.logout(getToken(),{
            success.invoke()
        },failure)
    }
}