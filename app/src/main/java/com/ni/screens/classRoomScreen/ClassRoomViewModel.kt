package com.ni.screens.classRoomScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.core.constants.Departments
import com.ni.models.ClassRoomModel
import com.ni.models.StudentInfoModel
import com.ni.repository.ClassRoomDataList
import com.ni.repository.StudentDataList

class ClassRoomViewModel : ViewModel() {

    private val _classRoomList = ClassRoomDataList
    private val _studentDataList = StudentDataList
    val classRoomDataList = MutableLiveData<List<ClassRoomModel>>()
    val studentDataList = MutableLiveData<List<StudentInfoModel>>()
    init {

        classRoomDataList.postValue(_classRoomList.classRoomList)
    }


    fun addToClassRoom(dept: String, sub: String, code: String) {
        _classRoomList.classRoomList.add(
            ClassRoomModel(
                "CR-3",
                dept,
                "",
                "",
                0,
                sub,
                code
            )
        )
        classRoomDataList.postValue(_classRoomList.classRoomList)
    }

    public fun getTitle(item: ClassRoomModel): String {
        return item.dept.toString() + " " + item.subjectCode + " : " + item.subject
    }

    public fun filterByClassRoom(classRoomId:String){
        Log.d("TAG", "filterByClassRoom: $classRoomId")
        var list = ArrayList<StudentInfoModel>()
        for(i in _studentDataList.studentList){
            Log.d("TAG", "filterByClassRoom: ${i}")
            if(i.classRoomIds.contains(classRoomId))
                list.add(i)
        }
        Log.d("TAG", "filterByClassRoom: ${list.size}")
        studentDataList.postValue(list)
    }
}