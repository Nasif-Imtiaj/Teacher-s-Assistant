package com.ni.database
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {
    companion object {
        fun addStudentsToDb(student: Students) {
            var databse = FirebaseDatabase.getInstance()
            var ref = databse.getReference("TeachersAssistant")
                .child("Students")
                .child(student.id!!)
            ref.setValue(student)
        }
    }
}