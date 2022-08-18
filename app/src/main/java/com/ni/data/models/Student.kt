package com.ni.data.models

data class Student(
    val id: String,
    val name: String,
    val studentId: String,
    val department: String,
    val batch: String,
    val section: String,
    val imgUrl: String,
) {
    constructor() : this("", "",
        "", "", "", "", "")
}
