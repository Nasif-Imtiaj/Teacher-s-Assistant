package com.ni.data.models


data class ClassListModel(
    val classRoomId: String,
    val dept: String,
    val batch: String,
    val section: String,
    val students: Int,
    val subject: String,
    val subjectCode: String
)
