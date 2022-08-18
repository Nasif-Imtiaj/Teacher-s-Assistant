package com.ni.data.models

data class Submission(
    val id: String,
    val studentId: String,
    val assignmentId: String,
    val answerScriptUrl: String,
) {
    constructor() : this("", "",
        "", "")
}
