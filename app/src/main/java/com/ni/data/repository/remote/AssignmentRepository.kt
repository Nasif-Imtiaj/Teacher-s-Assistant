package com.ni.data.repository.remote

import com.ni.data.models.Assignment

interface AssignmentRepository {
    fun create(assignment: Assignment)
    fun retrieve(roomId:String,callbacks: AssignmentCallbacks)
    fun delete(assignment: Assignment)
    fun update(assignment: Assignment)
}

interface AssignmentCallbacks{
    fun onSuccess(list:ArrayList<Assignment>)
    fun onFailed()
}