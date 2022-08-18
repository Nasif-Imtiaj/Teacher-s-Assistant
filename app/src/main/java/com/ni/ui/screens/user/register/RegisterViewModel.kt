package com.ni.ui.screens.user.register

import androidx.lifecycle.ViewModel
import com.ni.data.models.User
import com.ni.data.repository.remote.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun createUser(user: User) {
        userRepository.create(user)
    }
}