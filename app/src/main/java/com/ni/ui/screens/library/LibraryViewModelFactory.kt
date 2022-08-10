package com.ni.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ni.domain.usecases.library.LibraryUseCase

class LibraryViewModelFactory(): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
                return LibraryViewModel(LibraryUseCase()) as T;
            }
            throw IllegalArgumentException("Please provide the way how this viewModel can be initialized in LibraryViewModelFactory class")
        }
}