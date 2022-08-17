package com.ni.database.firebase.crud

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Classroom


class Retrieve {
    companion object {
        fun classroom():ArrayList<Classroom> {
            var databse = FirebaseDatabase.getInstance()
            var ref = databse.getReference("TeachersAssistant").child("Classroom")
            var list =ArrayList<Classroom>()
            ref.get().addOnSuccessListener{
                for (i in it.children) {
                    Log.d("RETRIEVE", "classroom: $i")
                    var classroom = i.getValue(Classroom::class.java)
                    if (classroom != null) {
                        list.add(classroom)
                    }
                    Log.d("RETRIEVE", "classroom: ${list.size}")
                }
            }
            Log.d("RETRIEVE", "classroom: ${list.size}")
            return list
        }
    }
}