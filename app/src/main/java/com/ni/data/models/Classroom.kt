package com.ni.data.models

data class Classroom(val id:String,val courseName:String,val batch:String,val section:String,val dateCreated:String)
{
    constructor() : this("", "",
        "", "","")
}