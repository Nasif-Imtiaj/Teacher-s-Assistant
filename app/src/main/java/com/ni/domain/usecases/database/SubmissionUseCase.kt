package com.ni.domain.usecases.database

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Submission
import com.ni.data.repository.remote.SubmissionRepository

class SubmissionUseCase:SubmissionRepository {
    override fun create(submission: Submission) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Submission")
            .child(submission.id)
        ref.setValue(submission)
    }

    override fun retrieve(submission: Submission) {
        TODO("Not yet implemented")
    }

    override fun update(submission: Submission) {
        TODO("Not yet implemented")
    }

    override fun delete(submission: Submission) {
        TODO("Not yet implemented")
    }
}