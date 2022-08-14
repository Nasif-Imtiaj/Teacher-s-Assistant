package com.ni.domain.usecases.library

import android.net.Uri
import android.os.Environment
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.ni.data.models.Booklet
import com.ni.data.repository.remote.LibraryRepository
import com.ni.utils.FileUtils
import java.io.File

class LibraryUseCase : LibraryRepository {
    val storage = FirebaseStorage.getInstance()
    override fun createFile(booklet: Booklet) {
        val fileName = booklet.name
        val storageRef = storage.reference.child("library/").child(fileName)
        val url = Uri.parse(booklet.localFileUrl)
        val uploadTask = storageRef.putFile(url)
        uploadTask.addOnFailureListener {

        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    override fun retrieveFile(booklet: Booklet) {
        val fileName = booklet.name
        val storageRef = storage.reference.child("library/").child(fileName)
        val newFolder = FileUtils.getRootDownloadDirectory()
        newFolder.mkdirs()
        val file = File(newFolder,booklet.name+".png")
        file.createNewFile()
        val downloadTask = storageRef.getFile(file)
        downloadTask.addOnFailureListener {
        }.addOnSuccessListener { taskSnapshot ->
            Log.e("TEST_download","success ${file.absolutePath}");
        }.addOnFailureListener{
            Log.e("TEST_download","Failed ", it)
        }
    }

    override fun updateFile(booklet: Booklet) {
        TODO("Not yet implemented")
    }

    override fun deleteFile(booklet: Booklet) {
        TODO("Not yet implemented")
    }


}