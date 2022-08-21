package com.ni.utils

import android.os.Environment
import com.ni.ui.activity.avmUser
import java.io.File

object FileUtils {
    fun getRootDownloadDirectory(): File {
        val downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        return  File(downloadLocation,"Teachers_Assistant/${avmUser.id}/")
    }
    fun getPdfSaveLocation():File{
        val downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        return  File(downloadLocation,"Teachers_Assistant/Results/")
    }
    fun isFileExistsOnDownload(fileName:String):Boolean{
        return File(getRootDownloadDirectory(),fileName).exists()
    }

}