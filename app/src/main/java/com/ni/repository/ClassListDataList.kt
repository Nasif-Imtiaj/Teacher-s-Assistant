package com.ni.repository

import com.ni.models.ClassListModel

object ClassListDataList {
   var classList = ArrayList<ClassListModel>()

    init {
        create_dummy()
    }

    fun create_dummy() {
        classList.add(
            ClassListModel(
                "CR-1",
                "CSE",
                "44",
                "B",
                24,
                "Project-400",
                "654"
            )
        )
        classList.add(
            ClassListModel(
                "CR-2",
                "CSE",
                "44",
                "B",
                24,
                "Project-300",
                "454"
            )
        )
        classList.add(
            ClassListModel(
                "CR-3",
                "CSE",
                "41",
                "C",
                45,
                "Project-200",
                "754"
            )
        )
        classList.add(
            ClassListModel(
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