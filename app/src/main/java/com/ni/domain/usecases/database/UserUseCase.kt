package com.ni.domain.usecases.database

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.User
import com.ni.data.repository.remote.UserCallBacks
import com.ni.data.repository.remote.UserRepository

class UserUseCase : UserRepository {
    override fun create(user: User) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("User")
            .child(user.id)
        ref.setValue(user)
    }

    override fun retrieve(currentUserId: String, callbacks: UserCallBacks) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant").child("User")
        var res = User()
        ref.get().addOnSuccessListener {
            for (i in it.children) {
                var user = i.getValue(User::class.java)
                if (user != null) {
                    if (user.id == currentUserId)
                        res = user
                }
            }
            callbacks.onSuccess(res)
        }.addOnFailureListener {
            callbacks.onFailed()
        }
    }

    override fun update(user: User) {
        TODO("Not yet implemented")
    }

    override fun delete(user: User) {
        TODO("Not yet implemented")
    }


}