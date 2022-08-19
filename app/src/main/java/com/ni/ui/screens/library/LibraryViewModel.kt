package com.ni.ui.screens.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ni.data.models.Booklet
import com.ni.data.repository.remote.FirebaseStorageCallbacks
import com.ni.data.repository.remote.FirebaseStorageRepository
import com.ni.data.repository.remote.LibraryRepository
import com.ni.ui.activity.avmUser
import com.ni.ui.common.baseClasses.BaseViewModel
import java.util.*

class LibraryViewModel(
    private val libraryRepository: LibraryRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository
) : BaseViewModel() {
    val libraryBookletList = MutableLiveData<List<Booklet>>()
    val _localFileUrl = MutableLiveData<String>()
    val localFileUrl: LiveData<String>
        get() = _localFileUrl
    val _remoteFileUrl = MutableLiveData<String>()
    val remoteFileUrl: LiveData<String>
        get() = _remoteFileUrl
    val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String>
        get() = _fileName

    init {}

    private fun uploadToStorage(
        folderName: String,
        subFolderName: String,
        fileName: String,
        localUri: String
    ) {
        firebaseStorageRepository.create(
            folderName,
            subFolderName,
            fileName,
            localUri,
            object : FirebaseStorageCallbacks {
                override fun onSuccess(url: String) {
                    _isLoading.postValue(false)
                    _remoteFileUrl.postValue(url)
                }

                override fun onFailed() {
                    _isLoading.postValue(false)
                    _toastMsg.postValue("Failed to submit, please try again")
                }
            }
        )
    }

    fun createBooklet(booklet: Booklet) {
        libraryRepository.createFile(booklet)
    }

    fun uploadBooklet() {
        createBooklet(
            Booklet(
                UUID.randomUUID().toString(),
                avmUser.id,
                fileName.value!!,
                localFileUrl.value!!,
                remoteFileUrl.value!!
            )
        )
    }

    fun downloadFile(booklet: Booklet) {
        libraryRepository.retrieveFile(booklet)
    }

    fun updateLocalFileUrl(url: String) {
        _localFileUrl.postValue(url)
    }

    fun updateFilename(name: String) {
        _fileName.postValue(name)
    }

    fun startFileUpload() {
        _isLoading.postValue(true)
        uploadToStorage(
            "library",
            avmUser.id,
            fileName.value!!,
            localFileUrl.value!!
        )
    }
}