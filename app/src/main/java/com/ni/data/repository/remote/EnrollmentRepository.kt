package com.ni.data.repository.remote

import com.ni.data.models.Enrollment

interface EnrollmentRepository {
    fun create(enrollment: Enrollment)
    fun retrieve(classroomId : String,callbacks: EnrollmentCallbacks)
    fun update(enrollment: Enrollment)
    fun delete(enrollment: Enrollment)
}

interface EnrollmentCallbacks{
    fun onSuccess(list:ArrayList<Enrollment>)
    fun onFailed()
}