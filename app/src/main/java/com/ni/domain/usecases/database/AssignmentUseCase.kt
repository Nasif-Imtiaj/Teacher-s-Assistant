package com.ni.domain.usecases.database

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Assignment
import com.ni.data.repository.remote.AssignmentRepository

class AssignmentUseCase : AssignmentRepository {

    override fun create(assignment: Assignment) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Assignment")
            .child(assignment.id)
        ref.setValue(assignment)
    }

    override fun retrieve() {

    }

    override fun update(assignment: Assignment) {

    }

    override fun delete(assignment: Assignment) {

    }


}