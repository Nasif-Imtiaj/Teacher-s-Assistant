package com.ni.data.repository.dummy

import com.ni.data.models.StudentInfoModel

object StudentDataList {
    var studentList = ArrayList<StudentInfoModel>()

    init {
        create_dummy()
    }

    fun create_dummy() {
        studentList.add(
            StudentInfoModel(
                arrayListOf( "CR-1","CR-1"),
                "181-115-045",
                "CSE",
                "44",
                "B",
                4,
                arrayListOf()
            )
        )
        studentList.add(
            StudentInfoModel(
                arrayListOf( "CR-1","CR-1"),
                "181-115-055",
                "CSE",
                "44",
                "B",
                4,
                arrayListOf()
            )
        )
        studentList.add(
            StudentInfoModel(
                arrayListOf( "CR-1","CR-3"),
                "181-115-058",
                "CSE",
                "44",
                "B",
                4,
                arrayListOf()
            )

        )
        studentList.add(
            StudentInfoModel(
                arrayListOf( "CR-2","CR-3"),
                "181-115-047",
                "CSE",
                "44",
                "B",
                4,
                arrayListOf()
            )
        )
    }
}