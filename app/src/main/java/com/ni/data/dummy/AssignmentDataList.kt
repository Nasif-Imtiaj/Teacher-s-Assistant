package com.ni.data.dummy

import com.ni.data.models.Assignment
import kotlin.collections.ArrayList

object AssignmentDataList {
    var list = ArrayList<Assignment>()
    init{
        createDummy()
    }
    private fun createDummy(){
        list.add(Assignment(
            "456456",
            "544645",
            "Assign-1",
            "01/02/22",
            "02/03/22",
            0
        ))
        list.add(Assignment(
            "45645656456",
            "32131",
            "Assign-1",
            "01/02/22",
            "02/03/22",
            0
        ))
    }
}