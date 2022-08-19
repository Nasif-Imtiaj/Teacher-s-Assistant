package com.ni.data.models

data class Submit(
    val id: String,
    val studentUid: String,
    val studentId: String,
    val assignmentId: String,
    val answerScriptUrl: String,
    val checked: Boolean,
    val obtained: Float,
    val bonus: Float,
    val penalty: Float,
    val total: Float,
) {
    constructor() : this(
        "", "", "",
        "", "", false, 0.0f, 0.0f, 0.0f, 0.0f
    )
}
