package com.ni.data.models

data class Assignment(
    val id: String,
    val classroomId: String,
    val name: String,
    val description: String,
    var batch:String,
    val startDate: String,
    val endDate: String,
    val mark: Int,
) {
    constructor() : this("", "",
        "", "", "","","", 0)
}
