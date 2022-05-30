package com.ni.models

import com.ni.core.constants.Departments

data class ClassRoomModel(val dept: Departments, val batch:Int, val section:String, val students:Int, val subject:String, val subjectCode:String)
