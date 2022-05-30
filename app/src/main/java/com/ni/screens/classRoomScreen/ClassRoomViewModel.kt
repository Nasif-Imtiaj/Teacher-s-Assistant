package com.ni.screens.classRoomScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.core.constants.Departments
import com.ni.models.ClassRoomModel

class ClassRoomViewModel : ViewModel() {

    private val classRoomInfoList = ArrayList<ClassRoomModel>()
    val classRoomDataList = MutableLiveData<List<ClassRoomModel>>()

    init {
        createDummyData()
    }

    private fun createDummyData(){
        classRoomInfoList.add(
            ClassRoomModel(
                Departments.CSE,
                44,
                "B",
                24,
                "Project-400",
                "CSE-654"
            )
        )
        classRoomInfoList.add(
            ClassRoomModel(
                Departments.CSE,
                44,
                "B",
                24,
                "Project-300",
                "CSE-454"
            )
        )
        classRoomDataList.postValue(classRoomInfoList)
    }
}