package com.hhsj.ilive.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

object FileUtils {

    val EXTERNAL_STORAGE: File = Environment.getExternalStorageDirectory()
    val AVATAR_CHILD_PATH = "${File.separator}iLiveAvatar${File.separator}"

    fun getAvatarFile(name: String): File{
        val file = File(
            EXTERNAL_STORAGE,
            AVATAR_CHILD_PATH + name
        )
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        return file
    }

    fun getAvatarUri(name: String): Uri{
        val file = getAvatarFile(name)
        return Uri.fromFile(file)
    }

    fun saveImageToGallery(context: Context, bitmap: Bitmap): Boolean{
        //保存图片
        val file = getAvatarFile("iLive_avatar_${System.currentTimeMillis()}.png")
        val fileOutputString = FileOutputStream(file)
        val result = bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputString)
        fileOutputString.flush()
        fileOutputString.close()

        val uri = Uri.fromFile(file)
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))

        return result
    }
}