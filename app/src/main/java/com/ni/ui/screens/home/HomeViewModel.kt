package com.ni.ui.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.ni.data.models.User
import com.ni.data.repository.remote.UserCallBacks
import com.ni.data.repository.remote.UserRepository
import com.ni.ui.common.baseClasses.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        retrieveUser()
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
                            Log.d("ISUSERCALLED", "onSuccess: ")
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

    fun getUserTpe():String{
        Log.d("ISUSERCALLED", "getUserTpe: ${user.value!!.userType}")
        return user.value?.userType ?: "Teacher"
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}