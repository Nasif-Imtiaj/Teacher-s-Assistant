package com.ni.domain.usecases.database

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Result
import com.ni.data.repository.remote.ResultRepository

class ResultUseCase:ResultRepository {
    override fun create(result: Result) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Result")
            .child(result.id)
        ref.setValue(result)
    }

    override fun retrieve(result: Result) {
        TODO("Not yet implemented")
    }

    override fun update(result: Result) {
        TODO("Not yet implemented")
    }

    override fun delete(result: Result) {
        TODO("Not yet implemented")
    }
}