package com.ni.data.dummy

import com.ni.data.models.Student

object StudentDataList {
    var list = ArrayList<Student>()

    init {
        createDummy()
    }

    private fun createDummy() {
        list.add(
            Student(
                "54fg54df6g",
                "Nasif",
                "181-115-045",
                "CSE",
                "44th",
                "B",
                ""
            )
        )
        list.add(
            Student(
                "456456",
                "Rahnama",
                "181-115-055",
                "CSE",
                "44th",
                "B",
                ""
            )
        )
        list.add(
            Student(
                "rtfdgfdg",
                "Hafiz",
                "181-115-050",
                "CSE",
                "44th",
                "B",
                ""
            )
        )
        list.add(
            Student(
                "ertertret",
                "Ratul",
                "181-115-050",
                "CSE",
                "44th",
                "B",
                ""
            )
        )
    }
}