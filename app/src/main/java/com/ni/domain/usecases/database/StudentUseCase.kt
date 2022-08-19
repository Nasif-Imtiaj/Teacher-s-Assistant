package com.ni.domain.usecases.database

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Enrollment
import com.ni.data.models.Student
import com.ni.data.repository.remote.StudentCallbacks
import com.ni.data.repository.remote.StudentRepository

class StudentUseCase : StudentRepository {
    override fun create(student: Student) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Student")
            .child(student.id)
        ref.setValue(student)
    }

    override fun retrieve(enrolledStudentList: ArrayList<String>, callbacks: StudentCallbacks) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant").child("Student")
        var list = ArrayList<Student>()
        ref.get().addOnSuccessListener {
            for (i in it.children) {
                var student = i.getValue(Student::class.java)
                if (student != null) {
                    if (enrolledStudentList.contains(student.id)) {
                        list.add(student)
                    }
                }
            }
            callbacks.onSuccess(list)
        }
    }

    override fun update(student: Student) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Student")
            .child(student.id)
        ref.setValue(student)
    }

    override fun delete(student: Student) {
        TODO("Not yet implemented")
    }
}