package com.ni.repository

import com.ni.models.ClassRoomModel

object ClassRoomDataList {
   var classRoomList = ArrayList<ClassRoomModel>()

    init {
        create_dummy()
    }

    fun create_dummy() {
        classRoomList.add(
            ClassRoomModel(
                "CR-1",
                "CSE",
                "44",
                "B",
                24,
                "Project-400",
                "654"
            )
        )
        classRoomList.add(
            ClassRoomModel(
                "CR-2",
                "CSE",
                "44",
                "B",
                24,
                "Project-300",
                "454"
            )
        )
        classRoomList.add(
            ClassRoomModel(
                "CR-3",
                "CSE",
                "41",
                "C",
                45,
                "Project-200",
                "754"
            )
        )
        classRoomList.add(
            ClassRoomModel(
                "CR-4",
                "CSE",
                "45",
                "A",
                20,
                "Project-100",
                "484"
            )
        )
    }
}