package com.ni.domain.usecases.database

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Submit
import com.ni.data.repository.remote.SubmitCallbacks
import com.ni.data.repository.remote.SubmitRepository

class SubmitUseCase : SubmitRepository {
    override fun create(submit: Submit) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Submit")
            .child(submit.id)
        ref.setValue(submit)
    }

    override fun retrieve(submit: Submit) {
        TODO("Not yet implemented")
    }

    override fun update(submit: Submit) {
        TODO("Not yet implemented")
    }

    override fun delete(submit: Submit) {
        TODO("Not yet implemented")
    }
}