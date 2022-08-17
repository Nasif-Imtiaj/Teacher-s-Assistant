package com.ni.data.repository.dummy

import com.ni.data.models.AssignmentModel
import java.util.*
import kotlin.collections.ArrayList

object AssignmentDataList {
    var assignmentList = ArrayList<AssignmentModel>()
    init{
        create_dummy()
    }
    fun create_dummy(){
        assignmentList.add(
            AssignmentModel(
                UUID.randomUUID().toString(),
                "AGN-1",
                "10/06/22",
                "18/06/22",
            )
        )
        assignmentList.add(
            AssignmentModel(
                UUID.randomUUID().toString(),
                "AGN-2",
                "11/06/22",
                "15/06/22",
            )
        )
    }
}