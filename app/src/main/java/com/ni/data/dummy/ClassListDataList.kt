package com.ni.data.dummy

import com.ni.data.models.Classroom

object ClassListDataList {
   var classList = ArrayList<Classroom>()

    init {
        create_dummy()
    }
    fun create_dummy() {
        classList.add(
            Classroom(
                "6546456546",
                "545446",
                "p-400",
                "CSE-335",
                "CSE",
                "44",
                "B",
                "16/06/22",
            )
        )
        classList.add(
            Classroom(
                "6546456546",
                "545446",
                "p-400",
                "CSE-335",
                "CSE",
                "44",
                "B",
                "16/06/22",
            )
        )
    }
}