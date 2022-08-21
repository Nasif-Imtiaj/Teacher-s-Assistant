package com.ni.data.models

data class Submit(
    val id: String,
    val studentUid: String,
    val studentId: String,
    val assignmentId: String,
    val answerScriptUrl: String,
    var checked: Boolean,
    var obtained: Float,
    var bonus: Float,
    var penalty: Float,
    var total: Float,
) {
    constructor() : this(
        "", "", "",
        "", "", false, 0.0f, 0.0f, 0.0f, 0.0f
    )
}
