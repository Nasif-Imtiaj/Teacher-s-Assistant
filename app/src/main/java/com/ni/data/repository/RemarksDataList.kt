package com.ni.data.repository

import com.ni.data.models.RemarksModel

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