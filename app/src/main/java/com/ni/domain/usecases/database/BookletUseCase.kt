package com.ni.domain.usecases.database

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Booklet
import com.ni.data.repository.remote.BookletCallBacks
import com.ni.data.repository.remote.BookletRepository

class BookletUseCase : BookletRepository {
    override fun createFile(booklet: Booklet) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse
            .getReference("TeachersAssistant")
            .child("Booklet")
            .child(booklet.id)
        ref.setValue(booklet)
    }

    override fun retrieveFile(userId: String, bookletCallBacks: BookletCallBacks) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse
            .getReference("TeachersAssistant")
            .child("Booklet")
        var list = ArrayList<Booklet>()
        ref.get().addOnSuccessListener {
            for (i in it.children) {
                var booklet = i.getValue(Booklet::class.java)
                if (booklet != null) {
                    if (booklet.userId == userId)
                        list.add(booklet)
                }
            }
            bookletCallBacks.onSuccess(list)
        }
    }

    override fun updateFile(booklet: Booklet) {
        TODO("Not yet implemented")
    }

    override fun deleteFile(booklet: Booklet) {
        TODO("Not yet implemented")
    }
}