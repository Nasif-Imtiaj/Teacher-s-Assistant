package com.ni.ui.screens.classRoom

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.AssignmentModel
import com.ni.data.models.StudentInfoModel
import com.ni.data.repository.dummy.AssignmentDataList
import com.ni.data.repository.dummy.StudentDataList

class ClassRoomViewModel : ViewModel() {
    val studentDataList = MutableLiveData<List<StudentInfoModel>>()
    private val _studentDataList = StudentDataList
    val assignmentDataList = MutableLiveData<List<AssignmentModel>>()
    private val _assignmentDataList = AssignmentDataList
    init {
        studentDataList.postValue(_studentDataList.studentList)
        assignmentDataList.postValue(_assignmentDataList.assignmentList)
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