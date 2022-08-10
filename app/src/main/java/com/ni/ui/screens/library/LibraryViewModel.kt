package com.ni.ui.screens.library

import androidx.lifecycle.ViewModel
import com.ni.data.models.Booklet
import com.ni.data.repository.remote.LibraryRepository

class LibraryViewModel(private val libraryRepository: LibraryRepository):ViewModel() {
    fun uploadFile(booklet: Booklet){
        libraryRepository.createFile(booklet)
    }
}