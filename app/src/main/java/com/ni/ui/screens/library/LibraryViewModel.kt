package com.ni.ui.screens.library

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ni.data.models.Booklet
import com.ni.data.repository.remote.BookletCallBacks
import com.ni.data.repository.remote.FirebaseStorageCallbacks
import com.ni.data.repository.remote.FirebaseStorageRepository
import com.ni.data.repository.remote.BookletRepository
import com.ni.ui.activity.avmUser
import com.ni.ui.common.baseClasses.BaseViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class LibraryViewModel(
    private val bookletRepository: BookletRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository
) : BaseViewModel() {
    private val FOLDERNAME = "Library"
    val _libraryBookletList = MutableLiveData<List<Booklet>>()
    val libraryBookletList: LiveData<List<Booklet>>
        get() = _libraryBookletList
    val _localFileUrl = MutableLiveData<String>()
    val localFileUrl: LiveData<String>
        get() = _localFileUrl
    val _remoteFileUrl = MutableLiveData<String>()
    val remoteFileUrl: LiveData<String>
        get() = _remoteFileUrl
    val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String>
        get() = _fileName
    val _showEmptyListMsg = MutableLiveData<Boolean>()
    val showEmptyListMsg: LiveData<Boolean>
        get() = _showEmptyListMsg


    init {
        retrieveBooklets()
    }

    fun updateLocalFileUrl(url: String) {
        _localFileUrl.postValue(url)
    }

    fun updateFilename(name: String) {
        _fileName.postValue(name)
    }


    private fun retrieveBooklets() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            retrieveBookletsAsync()
        }
    }

    private suspend fun retrieveBookletsAsync() {
        viewModelScope.launch {
            bookletRepository.retrieveFile(avmUser.id,
                object : BookletCallBacks {
                    override fun onSuccess(list: ArrayList<Booklet>) {
                        _isLoading.postValue(false)
                        _libraryBookletList.postValue(list)
                        _showEmptyListMsg.postValue((list.size)==0)
                    }

                    override fun onFailed() {
                        _isLoading.postValue(false)
                    }

                })
        }
    }

    fun uploadToStorage() {
        _isLoading.postValue(true)
        firebaseStorageRepository.create(
            FOLDERNAME,
            avmUser.id,
            fileName.value!!,
            localFileUrl.value!!,
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

    fun createBooklet() {
        bookletRepository.createFile(
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
        _isLoading.postValue(true)
        firebaseStorageRepository.retrieve(FOLDERNAME, avmUser.id,booklet.name,object : FirebaseStorageCallbacks{
            override fun onSuccess(url: String) {
                _toastMsg.postValue("Successfully downloaded.")
                _isLoading.postValue(false)
            }

            override fun onFailed() {
                _toastMsg.postValue("Failed to download!!")
            }

        })
    }

    fun getDownloadedFile(): File {
        val downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val filePath = "Teachers_Assistant/$FOLDERNAME/${avmUser.id}"
        return File(downloadLocation,filePath)
    }


}