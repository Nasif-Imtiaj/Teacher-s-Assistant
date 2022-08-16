package com.ni.ui.screens.classList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.ClassListModel
import com.ni.data.repository.ClassListDataList
import com.ni.database.firebase.crud.Create
import com.ni.database.firebase.crud.Retrieve
import com.ni.database.firebase.models.Classroom
import java.util.*

class ClassListViewModel : ViewModel() {
    private val _classRoomList = ClassListDataList

    val classRoomDataList = MutableLiveData<List<ClassListModel>>()

    init {
        var list = Retrieve.classroom()
        Log.d("TEST_DATA", ": ${list.size}")
        for(i in list)
            Log.d("TEST_DATA", ": $i")
        classRoomDataList.postValue(_classRoomList.classList)
    }

    fun addToClassRoom(courseName: String, department: String, batch: String) {
      /*  _classRoomList.classList.add(
            ClassListModel(
                "CR-3",
                dept,
                "",
                "",
                0,
                sub,
                code
            )
        )
        classRoomDataList.postValue(_classRoomList.classList)*/
        Create.classroom(Classroom(UUID.randomUUID().toString(),courseName,department,batch,"16/09/22"))
    }

    public fun getTitle(item: ClassListModel): String {
        return item.dept.toString() + " " + item.subjectCode + " : " + item.subject
    }
}