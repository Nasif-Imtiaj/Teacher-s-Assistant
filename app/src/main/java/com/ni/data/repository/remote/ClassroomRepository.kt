package com.ni.data.repository.remote

import com.ni.data.models.Classroom

interface ClassroomRepository {
    fun create(classroom: Classroom)
    fun retrieve(callbacks: ClassroomCallbacks)
    fun update(classroom: Classroom)
    fun delete(classroom: Classroom)
}

interface ClassroomCallbacks{
    fun onSuccess(list:ArrayList<Classroom>)
    fun onFailed()
}