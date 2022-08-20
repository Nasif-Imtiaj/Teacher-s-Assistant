package com.ni.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ni.domain.usecases.database.*
import com.ni.ui.screens.assignment.submission.SubmissionViewModel
import com.ni.ui.screens.assignment.submit.SubmitViewModel
import com.ni.ui.screens.classList.ClassListViewModel
import com.ni.ui.screens.classRoom.ClassRoomViewModel
import com.ni.ui.screens.home.HomeViewModel
import com.ni.ui.screens.library.LibraryViewModel
import com.ni.ui.screens.studentProfile.StudentProfileViewModel
import com.ni.ui.screens.teacherProfile.TeacherProfileViewModel
import com.ni.ui.screens.user.register.RegisterViewModel

class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            return LibraryViewModel(BookletUseCase(),FirebaseStorageUseCase()) as T;
        }
        if (modelClass.isAssignableFrom(ClassListViewModel::class.java)) {
            return ClassListViewModel(ClassroomUseCase()) as T
        }
        if (modelClass.isAssignableFrom(ClassRoomViewModel::class.java)) {
            return ClassRoomViewModel(
                EnrollmentUseCase(),
                StudentUseCase(),
                AssignmentUseCase()
            ) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(UserUseCase(), StudentUseCase()) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(UserUseCase()) as T
        }
        if (modelClass.isAssignableFrom(SubmitViewModel::class.java)) {
            return SubmitViewModel(SubmitUseCase(), FirebaseStorageUseCase()) as T
        }
        if (modelClass.isAssignableFrom(SubmissionViewModel::class.java)) {
            return SubmissionViewModel(SubmitUseCase()) as T
        }
        if (modelClass.isAssignableFrom(TeacherProfileViewModel::class.java)) {
            return TeacherProfileViewModel(UserUseCase(),FirebaseStorageUseCase()) as T
        }
        if(modelClass.isAssignableFrom(StudentProfileViewModel::class.java)){
            return StudentProfileViewModel(UserUseCase(),FirebaseStorageUseCase(),StudentUseCase()) as T
        }
        throw IllegalArgumentException("Please provide the way how this viewModel can be initialized in LibraryViewModelFactory class")
    }
}