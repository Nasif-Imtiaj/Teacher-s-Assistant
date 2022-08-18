package com.ni.ui.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.ni.data.models.Student
import com.ni.data.models.User
import com.ni.data.repository.remote.StudentCallbacks
import com.ni.data.repository.remote.StudentRepository
import com.ni.data.repository.remote.UserCallBacks
import com.ni.data.repository.remote.UserRepository
import com.ni.ui.activity.activityVmStudent
import com.ni.ui.common.baseClasses.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val studentRepository: StudentRepository
) : BaseViewModel() {

    val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user
    val _showNewStudentDialog = MutableLiveData<Boolean>()
    val showNewStudentDialog: LiveData<Boolean>
        get() = _showNewStudentDialog

    init {
        retrieveUser()
    }

    fun createStudent(student: Student) {
        studentRepository.create(student)
    }

    fun retrieveStudent() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            retrieveStudentAsync()
        }
    }

    private suspend fun retrieveStudentAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            var list = ArrayList<String>()
            list.add(FirebaseAuth.getInstance().currentUser!!.uid)
            studentRepository.retrieve(list, object : StudentCallbacks {
                override fun onSuccess(list: ArrayList<Student>) {
                    _isLoading.postValue(false)
                    if (list.size == 0) {
                        _showNewStudentDialog.postValue(true)
                    } else {
                        activityVmStudent = list[0]
                    }
                }

                override fun onFailed() {
                    _isLoading.postValue(false)
                }
            })
        }
    }

    fun retrieveUser() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            retrieveUserAsync()
        }
    }

    private suspend fun retrieveUserAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuth.getInstance().currentUser?.let {
                userRepository.retrieve(
                    it.uid, object : UserCallBacks {
                        override fun onSuccess(user: User) {
                            _isLoading.postValue(false)
                            _user.postValue(user)
                        }

                        override fun onFailed() {
                            _isLoading.postValue(false)
                        }
                    }
                )
            }
        }
    }

    fun getUserTpe(): String {
        return user.value?.userType ?: "Teacher"
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}