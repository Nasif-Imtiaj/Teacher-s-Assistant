package com.ni.database.firebase.crud

import com.google.firebase.database.FirebaseDatabase
import com.ni.database.firebase.models.Classroom
import com.ni.database.firebase.models.Enrollment
import com.ni.database.firebase.models.Student

class Create {
    companion object {
        fun student(student: Student) {
            var databse = FirebaseDatabase.getInstance()
            var ref = databse.getReference("TeachersAssistant")
                .child("Students")
                .child(student.id!!)
            ref.setValue(student)
        }

        fun classroom(classroom: Classroom) {
            var databse = FirebaseDatabase.getInstance()
            var ref = databse.getReference("TeachersAssistant")
                .child("Classroom")
                .child(classroom.id!!)
            ref.setValue(classroom)
        }

        fun enrollment(enrollment: Enrollment) {
            var databse = FirebaseDatabase.getInstance()
            var ref = databse.getReference("TeachersAssistant")
                .child("Enrollments")
                .child(enrollment.id!!)
            ref.setValue(enrollment)
        }
    }
}