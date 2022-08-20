package com.ni.data.repository.remote


interface FirebaseStorageRepository {
    fun create(folderName: String, subFolderName: String, fileName: String, localUri:String, firebaseStorageCallbacks: FirebaseStorageCallbacks)
    fun retrieve(folderName: String, subFolderName: String ,fileName:String, firebaseStorageCallbacks: FirebaseStorageCallbacks)
    fun update(folderName: String, subFolderName: String, fileName: String, localUri:String, firebaseStorageCallbacks: FirebaseStorageCallbacks)
    fun delete(folderName: String, subFolderName: String, fileName: String)
}

interface FirebaseStorageCallbacks{
    fun onSuccess(url:String)
    fun onFailed()
}