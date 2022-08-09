package com.ni.data.repository

import com.ni.data.models.AssignmentModel
object AssignmentDataList {
    var assignmentList = ArrayList<AssignmentModel>()
    init{
        create_dummy()
    }
    fun create_dummy(){
        assignmentList.add(
            AssignmentModel(
                "AGN-1",
                "10/06/22",
                "18/06/22",
            )
        )
        assignmentList.add(
            AssignmentModel(
                "AGN-2",
                "11/06/22",
                "15/06/22",
            )
        )
    }
}