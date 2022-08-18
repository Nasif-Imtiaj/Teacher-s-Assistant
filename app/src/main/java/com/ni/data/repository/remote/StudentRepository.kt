package com.ni.data.repository.remote

import com.ni.data.models.Student

interface StudentRepository {
    fun create(student: Student)
    fun retrieve(enrolledStudentList: ArrayList<String>,callbacks: StudentCallbacks)
    fun update(student: Student)
    fun delete(student: Student)
}
interface StudentCallbacks{
    fun onSuccess(list:ArrayList<Student>)
    fun onFailed()
}