package com.ni.data.repository.dummy

import com.ni.data.models.Submission

object SubmissionDataList {
    var list = ArrayList<Submission>()

    init {
        createDummy()
    }

    private fun createDummy() {
        list.add(
            Submission(
                "id",
                "studentId",
                "assigmentID",
                "answerscript"
            )
        )
    }
}