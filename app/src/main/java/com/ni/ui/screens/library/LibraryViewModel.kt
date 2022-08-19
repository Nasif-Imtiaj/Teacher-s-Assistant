package com.ni.ui.screens.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.Booklet
import com.ni.data.dummy.BookletDataList
import com.ni.data.repository.remote.FirebaseStorageRepository
import com.ni.data.repository.remote.LibraryRepository

class LibraryViewModel(
    private val libraryRepository: LibraryRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository
) : ViewModel() {
    private val _libraryBookletList = BookletDataList
    val libraryBookletList = MutableLiveData<List<Booklet>>()
    val _fileUrl = MutableLiveData<String>()
    val fileUrl: LiveData<String>
        get() = _fileUrl
    val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String>
        get() = _fileName

    init {
        libraryBookletList.postValue(_libraryBookletList.dummyList)
    }

    fun uploadFile(booklet: Booklet) {
        libraryRepository.createFile(booklet)
    }

    fun downloadFile(booklet: Booklet) {
        libraryRepository.retrieveFile(booklet)
    }

    fun updateFileUrl(url: String) {
        _fileUrl.postValue(url)
    }

    fun updateFilename(name: String) {
        _fileName.postValue(name)
    }

    fun startFileUpload(){

    }
}