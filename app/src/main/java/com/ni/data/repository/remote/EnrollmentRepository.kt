package com.ni.data.repository.remote

import com.ni.data.models.Enrollment

interface EnrollmentRepository {
    fun create(enrollment: Enrollment)
    fun retrieve(enrollment: Enrollment)
    fun update(enrollment: Enrollment)
    fun delete(enrollment: Enrollment)
}