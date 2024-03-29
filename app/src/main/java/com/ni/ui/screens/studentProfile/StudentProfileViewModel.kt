package com.ni.ui.screens.studentProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.Booklet
import com.ni.data.repository.remote.FirebaseStorageCallbacks
import com.ni.data.repository.remote.FirebaseStorageRepository
import com.ni.data.repository.remote.StudentRepository
import com.ni.data.repository.remote.UserRepository
import com.ni.ui.activity.avmStudent
import com.ni.ui.activity.avmUser
import com.ni.ui.activity.avmUserType
import com.ni.ui.activity.userIsTeacher
import com.ni.ui.common.baseClasses.BaseViewModel

class StudentProfileViewModel(
    private val userRepository: UserRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository,
    private val studentRepository: StudentRepository
) : BaseViewModel() {
    val _imgUrl = MutableLiveData<String>()
    val imgUrl: LiveData<String>
        get() = _imgUrl
    val _remoteUrl = MutableLiveData<String>()
    val remoteUrl: LiveData<String>
        get() = _remoteUrl

    fun updateUser(){
        userRepository.update(avmUser)
    }

    fun updateStudent(){
        studentRepository.update(avmStudent)
    }

    fun updateImgInDatabase() {
        _isLoading.postValue(true)
        var type = ""
        if (avmUserType == userIsTeacher)
            type = "teacher"
        else
            type = "student"
        firebaseStorageRepository.update(
            "profilePic",
            type,
            avmUser.id,
            imgUrl.value!!,
            object : FirebaseStorageCallbacks {
                override fun onSuccess(url: String) {
                    _toastMsg.postValue("Success")
                    _remoteUrl.postValue(url)
                    _isLoading.postValue(false)
                }

                override fun onFailed() {
                    _toastMsg.postValue("Failed")
                }

            }
        )
    }

    fun updateImgUrl(url: String) {
        _imgUrl.postValue(url)
    }
}