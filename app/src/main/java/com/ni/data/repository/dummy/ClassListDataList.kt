package com.ni.data.repository.dummy

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
                "p-400",
                "44",
                "B",
                "16/06/22",
            )
        )
        classList.add(
            Classroom(
                "456345453",
                "p-300",
                "46",
                "A",
                "17/06/22",
            )
        )
        classList.add(
            Classroom(
                "5454",
                "p-200",
                "42",
                "A",
                "15/06/22",
            )
        )
    }
}