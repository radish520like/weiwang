package com.hhsj.ilive.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hhsj.ilive.data.UserInfo
import com.hhsj.ilive.repository.UserInfoRepository
import com.hhsj.ilive.server.HttpServer

class UserInfoViewModel: ViewModel() {

    var phoneNumber: MutableLiveData<String> = MutableLiveData()
    var readChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    private var userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    private val userInfoRepository: UserInfoRepository = UserInfoRepository()

    private fun setUserInfo(info: UserInfo){
        userInfo.value = info
        userInfoRepository.saveUserInfo(info)
    }

    fun getToken() = userInfoRepository.getToken()
    fun getPhone() = phoneNumber.value?:userInfoRepository.getPhone()
    fun getHeader() = userInfoRepository.getHeader()
    fun getNickName() = userInfoRepository.getNickName()
    fun setToken(token: String) = userInfoRepository.setToken(token)

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
        setToken("")
        HttpServer.logout(getToken(),{
            success.invoke()
        },failure)
    }
}