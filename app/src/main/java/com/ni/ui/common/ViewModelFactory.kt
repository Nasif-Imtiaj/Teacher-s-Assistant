package com.ni.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ni.domain.usecases.database.ClassroomUseCase
import com.ni.domain.usecases.database.LibraryUseCase
import com.ni.ui.screens.classList.ClassListViewModel
import com.ni.ui.screens.library.LibraryViewModel

class ViewModelFactory(): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
                return LibraryViewModel(LibraryUseCase()) as T;
            }
            if(modelClass.isAssignableFrom(ClassListViewModel::class.java)){
                return ClassListViewModel(ClassroomUseCase())as T
            }
            throw IllegalArgumentException("Please provide the way how this viewModel can be initialized in LibraryViewModelFactory class")
        }
}