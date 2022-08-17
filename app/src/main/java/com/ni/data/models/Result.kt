package com.ni.data.models

data class Result(val id:String,val submissionId:String,val examMarks:Float,val obtained:Float
,val bonus:Float, val penalty:Float,val totalMarks: Float){
    constructor() : this("", "",
        0.0f, 0.0f,0.0f,0.0f,0.0f)
}
