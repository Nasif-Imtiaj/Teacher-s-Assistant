package com.ni.domain.usecases.database

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Assignment
import com.ni.data.models.Classroom
import com.ni.data.repository.remote.AssignmentCallbacks
import com.ni.data.repository.remote.AssignmentRepository

class AssignmentUseCase : AssignmentRepository {

    override fun create(assignment: Assignment) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Assignment")
            .child(assignment.id)
        ref.setValue(assignment)
    }

    override fun retrieve(roomId: String, callbacks: AssignmentCallbacks) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant").child("Assignment")
        var list = ArrayList<Assignment>()
        ref.get().addOnSuccessListener {
            for (i in it.children) {
                var assignment = i.getValue(Assignment::class.java)
                if (assignment != null) {
                    if (assignment.classroomId == roomId)
                        list.add(assignment)
                }
            }
            callbacks.onSuccess(list)
        }
    }

    override fun update(assignment: Assignment) {

    }

    override fun delete(assignment: Assignment) {

    }


}