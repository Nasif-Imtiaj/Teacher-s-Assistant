package com.ni.data.repository

import com.ni.data.constants.SubjectCode
import com.ni.data.models.MarksModel
import com.ni.data.models.SubmitModel

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
                MarksModel(30,10,20,40,50),
                "Hey this is a trial project submission test"
            )
        )
        submissionDataList.add(
            SubmitModel(
                "181-115-055",
                "11/06/22,11:59pm",
                SubjectCode.P_300,
                MarksModel(40,0,0,40,50),
                "Hey this is a trial project submission test2"
            )
        )
        submissionDataList.add(
            SubmitModel(
                "181-115-058",
                "11/06/22,11:59pm",
                SubjectCode.P_300,
                MarksModel(20,10,0,10,50),
                "Hey this is a trial project submission test2"
            )
        )
    }
}