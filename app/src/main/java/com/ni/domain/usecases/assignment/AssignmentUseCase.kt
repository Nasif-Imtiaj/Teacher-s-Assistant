package com.ni.domain.usecases.assignment

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.AssignmentModel
import com.ni.data.repository.remote.AssignmentRepository
import com.ni.teachersassistant.BuildConfig

private val debugRef = "debug/"
private val releaseRef = "production/"
private val assignment = "assignment"

class  AssignmentUseCase : AssignmentRepository {
    override fun getAssignment(): AssignmentModel {
        TODO("Not yet implemented")
    }

    override fun createAssignment(assignmentModel: AssignmentModel) {
        val reference = FirebaseDatabase.getInstance()
            .reference
            .child(if (BuildConfig.DEBUG) debugRef else releaseRef)
            .child(assignment)
            .child(assignmentModel.id)
            .push()
        reference.setValue(assignmentModel)
    }

    override fun deleteAssignment(assignmentModel: AssignmentModel) {
        TODO("Not yet implemented")
    }

    override fun updateAssignment(assignmentModel: AssignmentModel) {
        val reference = FirebaseDatabase.getInstance()
            .reference
            .child(if (BuildConfig.DEBUG) debugRef else releaseRef)
            .child(assignment)
            .child(assignmentModel.id)
            .push()
        reference.setValue(assignmentModel)
    }
}