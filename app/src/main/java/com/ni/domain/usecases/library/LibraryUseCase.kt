package com.ni.domain.usecases.library

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.ni.data.models.Booklet
import com.ni.data.repository.remote.LibraryRepository
import java.io.File

class LibraryUseCase : LibraryRepository {
    val storage = FirebaseStorage.getInstance()
    override fun getFile(booklet: Booklet) {
        var fileName = booklet.name
        val storageRef = storage.reference.child("library/").child(fileName)
        val localFile = File.createTempFile("temp", "txt")
        var downloadTask = storageRef.getFile(localFile)
        downloadTask.addOnFailureListener {
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    override fun createFile(booklet: Booklet) {
        var fileName = booklet.name
        var storageRef = storage.reference.child("library/").child(fileName)
        val url = Uri.parse(booklet.url)
        var uploadTask = storageRef.putFile(url)

        uploadTask.addOnFailureListener {

        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }
}