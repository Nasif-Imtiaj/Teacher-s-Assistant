package com.ni.ui.screens.teacherProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.repository.remote.FirebaseStorageCallbacks
import com.ni.data.repository.remote.FirebaseStorageRepository
import com.ni.data.repository.remote.UserRepository
import com.ni.ui.activity.avmUser
import com.ni.ui.activity.avmUserType
import com.ni.ui.activity.userIsTeacher
import com.ni.ui.common.baseClasses.BaseViewModel

class TeacherProfileViewModel(private val userRepository: UserRepository,private val firebaseStorageRepository: FirebaseStorageRepository) : BaseViewModel() {
    val _imgUrl = MutableLiveData<String>()
    val imgUrl: LiveData<String>
        get() = _imgUrl
    val _remoteUrl = MutableLiveData<String>()
    val remoteUrl: LiveData<String>
        get() = _remoteUrl

    fun updateUser(){
        userRepository.update(avmUser)
    }
    fun updateImgInDatabase(){
        _isLoading.postValue(true)
        var type =""
        if(avmUserType == userIsTeacher)
            type = "teacher"
        else
            type = "student"
        firebaseStorageRepository.update(
            "profilePic",
            type,
            avmUser.id,
            imgUrl.value!!,
            object : FirebaseStorageCallbacks{
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

    fun updateImgUrl(url:String){
        _imgUrl.postValue(url)
    }
}