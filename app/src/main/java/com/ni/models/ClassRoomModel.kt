package com.ni.models


data class ClassRoomModel(
    val classRoomId: String,
    val dept: String,
    val batch: String,
    val section: String,
    val students: Int,
    val subject: String,
    val subjectCode: String
)
