package com.ni.database.firebase.crud

import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.*

class Create {
    companion object {
        fun student(student: Student) {
            var databse = FirebaseDatabase.getInstance()
            var ref = databse.getReference("TeachersAssistant")
                .child("Student")
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
                .child("Enrollment")
                .child(enrollment.id!!)
            ref.setValue(enrollment)
        }

        fun assignments(assignment: Assignment) {
            var databse = FirebaseDatabase.getInstance()
            var ref = databse.getReference("TeachersAssistant")
                .child("Assignment")
                .child(assignment.id!!)
            ref.setValue(assignment)
        }

        fun submission(submission: Submission) {
            var databse = FirebaseDatabase.getInstance()
            var ref = databse.getReference("TeachersAssistant")
                .child("Submission")
                .child(submission.id!!)
            ref.setValue(submission)
        }

        fun result(result: Result) {
            var databse = FirebaseDatabase.getInstance()
            var ref = databse.getReference("TeachersAssistant")
                .child("Result")
                .child(result.id!!)
            ref.setValue(result)
        }
    }
}