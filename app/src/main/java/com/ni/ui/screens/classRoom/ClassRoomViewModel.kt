package com.ni.ui.screens.classRoom

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.Assignment
import com.ni.data.models.Student
import com.ni.data.repository.dummy.AssignmentDataList
import com.ni.data.repository.dummy.StudentDataList

class ClassRoomViewModel : ViewModel() {
    val studentDataList = MutableLiveData<List<Student>>()
    private val _studentDataList = StudentDataList
    val assignmentDataList = MutableLiveData<List<Assignment>>()
    private val _assignmentDataList = AssignmentDataList
    init {
        studentDataList.postValue(_studentDataList.list)
        assignmentDataList.postValue(_assignmentDataList.list)
    }
    fun filterByClassRoom(classRoomId: String) {
        var list = ArrayList<Student>()
        for (i in _studentDataList.list) {

            if (i.id.contains(classRoomId))
                list.add(i)
        }
        Log.d("TAG", "filterByClassRoom: ${list.size}")
        studentDataList.postValue(list)
    }

}