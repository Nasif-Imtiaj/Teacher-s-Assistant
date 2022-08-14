package com.ni.ui.screens.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.Booklet
import com.ni.data.repository.BookletDataList
import com.ni.data.repository.remote.LibraryRepository

class LibraryViewModel(private val libraryRepository: LibraryRepository):ViewModel() {
    private val _libraryBookletList = BookletDataList
    val libraryBookletList = MutableLiveData<List<Booklet>>()
    init {
        libraryBookletList.postValue(_libraryBookletList.dummyList)
    }
    fun uploadFile(booklet: Booklet){
        libraryRepository.createFile(booklet)
    }
    fun downloadFile(booklet: Booklet) {
        libraryRepository.retrieveFile(booklet)
    }


}