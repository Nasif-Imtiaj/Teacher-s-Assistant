package com.ni.data.models

data class Student(
    val id: String,
    var name: String,
    var studentId: String,
    var department: String,
    var batch: String,
    var section: String,
    val imgUrl: String,
) {
    constructor() : this("", "",
        "", "", "", "", "")
}
