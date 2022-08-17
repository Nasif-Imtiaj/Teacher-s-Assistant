package com.ni.domain.usecases.database

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Classroom
import com.ni.data.repository.remote.ClassroomCallbacks
import com.ni.data.repository.remote.ClassroomRepository

class ClassroomUseCase :ClassroomRepository{
    override fun create(classroom: Classroom) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Classroom")
            .child(classroom.id)
        ref.setValue(classroom)
    }

    override fun retrieve(callbacks: ClassroomCallbacks) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant").child("Classroom")
        var list =ArrayList<Classroom>()
        ref.get().addOnSuccessListener{
            for (i in it.children) {
                var classroom = i.getValue(Classroom::class.java)
                if (classroom != null) {
                    list.add(classroom)
                }
            }
            callbacks.onSuccess(list)
        }

    }

    override fun update(classroom: Classroom) {
        TODO("Not yet implemented")
    }

    override fun delete(classroom: Classroom) {
        TODO("Not yet implemented")
    }
}