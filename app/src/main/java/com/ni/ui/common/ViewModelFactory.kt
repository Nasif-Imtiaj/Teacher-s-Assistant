package com.ni.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ni.data.repository.remote.StudentRepository
import com.ni.domain.usecases.database.*
import com.ni.ui.screens.classList.ClassListViewModel
import com.ni.ui.screens.classRoom.ClassRoomViewModel
import com.ni.ui.screens.library.LibraryViewModel

class ViewModelFactory(): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
                return LibraryViewModel(LibraryUseCase()) as T;
            }
            if(modelClass.isAssignableFrom(ClassListViewModel::class.java)){
                return ClassListViewModel(ClassroomUseCase())as T
            }
            if(modelClass.isAssignableFrom(ClassRoomViewModel::class.java))
            {
                return ClassRoomViewModel(EnrollmentUseCase(),StudentUseCase(),AssignmentUseCase())as T
            }
            throw IllegalArgumentException("Please provide the way how this viewModel can be initialized in LibraryViewModelFactory class")
        }
}