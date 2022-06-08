package com.ni.screens.classRoomScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.models.StudentInfoModel
import com.ni.repository.StudentDataList

class ClassRoomViewModel : ViewModel() {
    val studentDataList = MutableLiveData<List<StudentInfoModel>>()
    private val _studentDataList = StudentDataList
    init {
        studentDataList.postValue(_studentDataList.studentList)
    }
    fun filterByClassRoom(classRoomId: String) {
        var list = ArrayList<StudentInfoModel>()
        for (i in _studentDataList.studentList) {

            if (i.classRoomIds.contains(classRoomId))
                list.add(i)
        }
        Log.d("TAG", "filterByClassRoom: ${list.size}")
        studentDataList.postValue(list)
    }

}