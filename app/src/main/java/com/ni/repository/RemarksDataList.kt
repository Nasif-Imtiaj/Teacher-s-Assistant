package com.ni.repository

import com.ni.models.RemarksModel

object RemarksDataList {
    var remarksList = ArrayList<RemarksModel>()
    init{
        createDummy()
    }

    fun createDummy(){
        remarksList.add(
            RemarksModel(
                "181-115-045",
                "hello programmer"
            )
        )
    }
}