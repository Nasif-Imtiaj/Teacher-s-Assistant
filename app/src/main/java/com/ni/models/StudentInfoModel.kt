package com.ni.models

data class StudentInfoModel(
    val classRoomIds: ArrayList<String>,
    val studentId: String,
    val dept: String,
    val batch: String,
    val section: String,
    val img:Int,
    val subjectWiseMarks: ArrayList<Pair<String,Float>>
)
