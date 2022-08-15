package com.ni.utils

import android.os.Environment
import com.ni.data.models.Booklet
import java.io.File

object FileUtils {
    fun getRootDownloadDirectory(): File {
        val downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        return  File(downloadLocation,"Library_Materials/")
    }
    fun isFileExistsOnDownload(fileName:String):Boolean{
        return File(getRootDownloadDirectory(),fileName).exists()
    }
}