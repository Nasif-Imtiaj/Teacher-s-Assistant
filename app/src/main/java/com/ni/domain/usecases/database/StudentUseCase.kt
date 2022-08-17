package com.ni.domain.usecases.database

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Student
import com.ni.data.repository.remote.StudentRepository

class StudentUseCase:StudentRepository {
    override fun create(student: Student) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Student")
            .child(student.id)
        ref.setValue(student)
    }

    override fun retrieve(student: Student) {
        TODO("Not yet implemented")
    }

    override fun update(student: Student) {
        TODO("Not yet implemented")
    }

    override fun delete(student: Student) {
        TODO("Not yet implemented")
    }
}