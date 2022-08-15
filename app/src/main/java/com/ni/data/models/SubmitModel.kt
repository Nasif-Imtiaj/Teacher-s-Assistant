package com.ni.data.models

import com.ni.data.constants.SubjectCode

data class SubmitModel(val studentID:String, val submissionTime:String, val subjectCode:SubjectCode, val marks:MarksModel, val report:String)
