package com.ni.domain.usecases.database

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Enrollment
import com.ni.data.repository.remote.EnrollmentRepository

class EnrollmentUseCase :EnrollmentRepository{
    override fun create(enrollment: Enrollment) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Enrollment")
            .child(enrollment.id)
        ref.setValue(enrollment)
    }

    override fun retrieve(enrollment: Enrollment) {
        TODO("Not yet implemented")
    }

    override fun update(enrollment: Enrollment) {
        TODO("Not yet implemented")
    }

    override fun delete(enrollment: Enrollment) {
        TODO("Not yet implemented")
    }
}