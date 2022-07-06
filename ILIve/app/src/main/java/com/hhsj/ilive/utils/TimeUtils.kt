package com.hhsj.ilive.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun getCurrentDataFormatTime(): String{
        val simpleDataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return simpleDataFormat.format(Date())
    }
}