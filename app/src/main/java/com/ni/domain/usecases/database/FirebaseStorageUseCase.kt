package com.ni.domain.usecases.database

import android.net.Uri
import android.os.Environment
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.ni.data.repository.remote.FirebaseStorageCallbacks
import com.ni.data.repository.remote.FirebaseStorageRepository
import com.ni.ui.activity.avmUser
import com.ni.utils.FileUtils
import java.io.File


class FirebaseStorageUseCase : FirebaseStorageRepository {
    override fun create(
        folderName: String,
        subFolderName: String,
        fileName: String,
        localUri: String,
        firebaseStorageCallbacks: FirebaseStorageCallbacks
    ) {
        val storageRef =
            FirebaseStorage.getInstance().reference.child("$folderName/").child("$subFolderName/")
                .child(fileName)
        val url = Uri.parse(localUri)
        storageRef.putFile(url).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri -> // getting image uri and converting into string

                firebaseStorageCallbacks.onSuccess(uri.toString())
            }.addOnFailureListener {
                firebaseStorageCallbacks.onFailed()
            }
        }
    }

    override fun retrieve(
        folderName: String, subFolderName: String, fileName: String,
        firebaseStorageCallbacks: FirebaseStorageCallbacks
    ) {
        val storageRef = FirebaseStorage
            .getInstance()
            .reference
            .child(folderName)
            .child(avmUser.id)
            .child(fileName)
        Log.e("TEST_download", "success ${fileName}");
        val newFolder = FileUtils.getRootDownloadDirectory()
        newFolder.mkdirs()
        val file = File(newFolder,fileName)
        file.createNewFile()
        val downloadTask = storageRef.getFile(file)
        downloadTask.addOnFailureListener {
        }.addOnSuccessListener { taskSnapshot ->
            Log.e("TEST_download", "success ${file.absolutePath}");
            firebaseStorageCallbacks.onSuccess("Success")

        }.addOnFailureListener {
            firebaseStorageCallbacks.onSuccess("Failed")
        }
    }

    override fun update(
        folderName: String,
        subFolderName: String,
        fileName: String,
        localUri: String,
        firebaseStorageCallbacks: FirebaseStorageCallbacks
    ) {
        val storageRef =
            FirebaseStorage.getInstance().reference.child("$folderName/").child("$subFolderName/")
                .child(fileName)
        val url = Uri.parse(localUri)
        storageRef.putFile(url).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri -> // getting image uri and converting into string

                firebaseStorageCallbacks.onSuccess(uri.toString())
            }.addOnFailureListener {
                firebaseStorageCallbacks.onFailed()
            }
        }
    }

    override fun delete(folderName: String, subFolderName: String, fileName: String) {
        TODO("Not yet implemented")
    }
}