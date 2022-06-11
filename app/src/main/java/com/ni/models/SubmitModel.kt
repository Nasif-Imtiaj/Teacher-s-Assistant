package com.ni.models

import com.ni.core.constants.SubjectCode

data class SubmitModel(val studentID:String, val submissionTime:String, val subjectCode:SubjectCode, val marks:MarksModel, val report:String)
