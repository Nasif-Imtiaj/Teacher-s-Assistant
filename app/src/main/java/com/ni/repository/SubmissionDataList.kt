package com.ni.repository

import com.ni.core.constants.SubjectCode
import com.ni.models.MarksModel
import com.ni.models.SubmitModel

object SubmissionDataList {
    var submissionDataList = ArrayList<SubmitModel>()
    init {
        createDummy()
    }
    fun createDummy(){
        submissionDataList.add(
            SubmitModel(
                "181-115-045",
                "11/06/22,11:59pm",
                SubjectCode.P_300,
                MarksModel(0,0,0,0,0),
                "Hey this is a trial project submission test"
            )
        )
        submissionDataList.add(
            SubmitModel(
                "181-115-055",
                "11/06/22,11:59pm",
                SubjectCode.P_300,
                MarksModel(0,0,0,0,0),
                "Hey this is a trial project submission test2"
            )
        )
        submissionDataList.add(
            SubmitModel(
                "181-115-058",
                "11/06/22,11:59pm",
                SubjectCode.P_300,
                MarksModel(0,0,0,0,0),
                "Hey this is a trial project submission test2"
            )
        )
    }
}