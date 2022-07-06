package com.hhsj.ilive.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hhsj.ilive.data.UserInfo
import com.hhsj.ilive.data.UserInfoData
import com.hhsj.ilive.repository.UserInfoRepository
import com.hhsj.ilive.server.HttpServer

class UserInfoViewModel : ViewModel() {

    var phoneNumber: MutableLiveData<String> = MutableLiveData()
    var readChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    private var userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    private val userInfoRepository: UserInfoRepository = UserInfoRepository()

    private fun setUserInfo(info: UserInfo) {
        userInfo.value = info
        userInfoRepository.saveUserInfo(info)
    }

    fun getId() = userInfoRepository.getUserID()
    fun getToken() = userInfoRepository.getToken()
    fun getPhone() = phoneNumber.value ?: userInfoRepository.getPhone()
    fun getHeader() = userInfoRepository.getHeader()
    fun getNickName() = userInfoRepository.getNickName()
    fun getAccount() = userInfoRepository.getAccount()
    fun getNickNameLimit() = userInfoRepository.getNickNameLimit()
    fun getAccountLimit() = userInfoRepository.getAccountLimit()

    fun setToken(token: String) = userInfoRepository.setToken(token)
    fun setNickName(nickName: String) = userInfoRepository.setNickName(nickName)
    fun setPhone(phone: String) = userInfoRepository.setPhone(phone)
    fun setAccount(account: String) = userInfoRepository.setAccount(account);
    fun setHeader(header: String) = userInfoRepository.setHeader(header)

    fun getVerifyCode(success: () -> Unit, failure: (String?) -> Unit) {
        HttpServer.getVerifyCode(phoneNumber.value ?: getPhone(), success, failure)
    }

    fun getVerifyCodeExpectLoginOrRegister(
        phone: String = "",
        success: () -> Unit,
        failure: (String?) -> Unit
    ) {
        HttpServer.getVerifyCode(getToken(), phone, success, failure)
    }

    fun registerOrLogin(
        phone: String,
        code: String,
        success: (UserInfo) -> Unit,
        failure: (String?) -> Unit
    ) {
        HttpServer.registerOrLogin(phone, code, {
            setUserInfo(it)
            success.invoke(it)
        }, failure)
    }

    fun logout(success: () -> Unit, failure: () -> Unit) {
        setToken("")
        HttpServer.logout(getToken(), {
            success.invoke()
        }, failure)
    }

    fun getUserInfo(success: (UserInfoData) -> Unit, failure: (String) -> Unit) {
        HttpServer.getUserInfo(getToken(), {
            userInfoRepository.saveUserInfoWithoutToken(it)
            success.invoke(it.data)
        }, failure)
    }

    fun updateUserInfo(
        nickName: String = "",
        account: String = "",
        header: String = "",
        phone: String = "",
        success: () -> Unit,
        failure: (String?) -> Unit
    ) {
        HttpServer.updateUserInfo(getToken(), nickName, account, header, phone, {
            if (nickName.isNotEmpty()) setNickName(nickName)
            if (account.isNotEmpty()) setAccount(account)
            if (header.isNotEmpty()) setHeader(header)
            if (phone.isNotEmpty()) setPhone(phone)
            success()
        }, failure)
    }

    fun checkPhone(
        phone: String = "",
        code: String,
        success: () -> Unit,
        failure: (String) -> Unit
    ) {
        HttpServer.checkPhone(getToken(),phone,code,success,failure)
    }
}