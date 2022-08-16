package com.ni.database

data class Students(val id:String,
                    val name:String, val studentId:String, val  department:String, val batch:String, val section:String)
{
    constructor() : this("", "",
        "", "","","")
}
