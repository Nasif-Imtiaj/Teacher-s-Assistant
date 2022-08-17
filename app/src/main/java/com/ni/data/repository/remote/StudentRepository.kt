package com.ni.data.repository.remote

import com.ni.data.models.Student

interface StudentRepository {
    fun create(student: Student)
    fun retrieve(student: Student)
    fun update(student: Student)
    fun delete(student: Student)
}