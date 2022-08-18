package com.ni.data.repository.remote

import com.ni.data.models.User


interface UserRepository {
    fun create(user: User)
    fun retrieve(currentUserId: String, callbacks: UserCallBacks)
    fun update(user: User)
    fun delete(user: User)
}

interface UserCallBacks{
    fun onSuccess(user: User)
    fun onFailed()
}