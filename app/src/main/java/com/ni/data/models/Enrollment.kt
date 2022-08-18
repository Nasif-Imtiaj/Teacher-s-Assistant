package com.ni.data.models

data class Enrollment(val id:String, val studentId:String, val classroomId:String)
{
    constructor():this("","","")
}