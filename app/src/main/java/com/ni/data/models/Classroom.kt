package com.ni.data.models

data class Classroom(
    val id: String,
    val creatorId: String,
    val courseName: String,
    val courseCode: String,
    val department: String,
    val batch: String,
    val section: String,
    val dateCreated: String,
) {
    constructor() : this(
        "", "",
        "", "", "", "", "", ""
    )
}