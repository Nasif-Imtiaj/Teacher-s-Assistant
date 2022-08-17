package com.ni.domain.usecases.database

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Assignment
import com.ni.data.repository.remote.AssignmentRepository

class AssignmentUseCase : AssignmentRepository {
    override fun retrieve() {

    }

    override fun create(assignment: Assignment) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Assignment")
            .child(assignment.id)
        ref.setValue(assignment)
    }

    override fun delete(assignment: Assignment) {

    }

    override fun update(assignment: Assignment) {

    }
}