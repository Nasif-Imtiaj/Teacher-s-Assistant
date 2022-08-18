package com.ni.domain.usecases.database

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Enrollment
import com.ni.data.repository.remote.EnrollmentCallbacks
import com.ni.data.repository.remote.EnrollmentRepository

class EnrollmentUseCase :EnrollmentRepository{
    override fun create(enrollment: Enrollment) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Enrollment")
            .child(enrollment.id)
        ref.setValue(enrollment)
    }

    override fun retrieve(classroomId: String, callbacks: EnrollmentCallbacks) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant").child("Enrollment")
        var list =ArrayList<Enrollment>()
        ref.get().addOnSuccessListener{
            for (i in it.children) {
                var enrollment = i.getValue(Enrollment::class.java)
                if (enrollment != null) {
                    if(enrollment.classroomId == classroomId)
                        list.add(enrollment)
                }
            }
            callbacks.onSuccess(list)
        }
    }


    override fun update(enrollment: Enrollment) {
        TODO("Not yet implemented")
    }

    override fun delete(enrollment: Enrollment) {
        TODO("Not yet implemented")
    }
}