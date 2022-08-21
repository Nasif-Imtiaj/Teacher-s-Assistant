package com.ni.domain.usecases.database

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.ni.data.models.Student
import com.ni.data.models.Submit
import com.ni.data.repository.remote.SubmitCallbacks
import com.ni.data.repository.remote.SubmitRepository

class SubmitUseCase : SubmitRepository {
    override fun create(submit: Submit) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Submit")
            .child(submit.id)
        ref.setValue(submit)
    }

    override fun retrieve(assignmentId: String, submitCallbacks: SubmitCallbacks) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant").child("Submit")
        var list = ArrayList<Submit>()
        ref.get().addOnSuccessListener {
            for (i in it.children) {
                var submit = i.getValue(Submit::class.java)
                Log.d("TESTID", "initGetArguments:3  $submit")
                if (submit != null) {
                    if (submit.assignmentId == assignmentId) {
                        list.add(submit)
                    }
                }
            }
            submitCallbacks.onSuccess(list)
        }
    }

    override fun update(submit: Submit) {
        var databse = FirebaseDatabase.getInstance()
        var ref = databse.getReference("TeachersAssistant")
            .child("Submit")
            .child(submit.id)
        ref.setValue(submit)
    }

    override fun delete(submit: Submit) {
        TODO("Not yet implemented")
    }
}