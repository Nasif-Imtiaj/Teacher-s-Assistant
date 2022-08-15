package com.ni.data.repository
import com.ni.data.models.Booklet
object BookletDataList {
    var dummyList = ArrayList<Booklet>()

    init {
        create_dummy()
    }
    fun create_dummy() {
        dummyList.add(
            Booklet(
                "CR-1",
                "Test",
                "",
                "",
                "https://firebasestorage.googleapis.com/v0/b/ni-teacher-assistant.appspot.com/o/library%2FTest?alt=media&token=c94323ef-99ca-42ec-a6f8-07d797b2efb2",
                "https://firebasestorage.googleapis.com/v0/b/ni-teacher-assistant.appspot.com/o/library%2FtestName?alt=media&token=b0f13ac6-0d3d-4c0f-b4e0-8dedad0f9603"
            )
        )
    }
}