package com.ni.domain.usecases.database

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.ni.data.repository.remote.FirebaseStorageCallbacks
import com.ni.data.repository.remote.FirebaseStorageRepository


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

    override fun retrieve(folderName: String, subFolderName: String, fileName: String) {
        TODO("Not yet implemented")
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