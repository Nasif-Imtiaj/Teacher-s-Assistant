package com.ni.ui.screens.classList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ni.data.repository.dummy.ClassListDataList
import com.ni.data.models.Classroom
import com.ni.data.repository.remote.ClassroomCallbacks
import com.ni.data.repository.remote.ClassroomRepository
import com.ni.ui.common.baseClasses.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ClassListViewModel(private val classroomRepository: ClassroomRepository) : BaseViewModel() {
    private val dummyClassRoomList = ClassListDataList
    private val _classRoomDataList = MutableLiveData<ArrayList<Classroom>>()
    val classRoomDataList: LiveData<ArrayList<Classroom>>
        get() = _classRoomDataList
    private var colorList = ArrayList<String>()
    private var idx = 0

    init {
        _classRoomDataList.postValue(dummyClassRoomList.classList)
        retrieveClassrooms()
        colorList.add("#d8d8d8")
        colorList.add("#BD30FF")
        colorList.add("#3038FF")
        colorList.add("#4690FE")
        colorList.add("#8146FE")
        colorList.add("#DF9025")
        colorList.add("#2A428C")
        colorList.add("#FF5630")
        colorList.add("#9DB55F")
        colorList.add("#000000")
        colorList.add("#DFC49C")
        colorList.add("#172B4D")
        colorList.add("#42D292")
    }

    private fun createClassroom(classroom: Classroom) {
        classroomRepository.create(classroom)
    }

    private fun retrieveClassrooms() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            retrieveClassroomsAsync()
        }
    }

    private suspend fun retrieveClassroomsAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            classroomRepository.retrieve(object : ClassroomCallbacks {
                override fun onSuccess(list: ArrayList<Classroom>) {
                    viewModelScope.launch(Dispatchers.Main) {
                        _isLoading.postValue(false)
                        _classRoomDataList.postValue(list)
                    }
                }

                override fun onFailed() {
                    viewModelScope.launch(Dispatchers.Main) {
                        _isLoading.postValue(false)
                    }
                }
            })
        }
    }


    fun addToClassRoom(courseName: String,courseCode:String, department: String, batch: String) {
        createClassroom(Classroom(UUID.randomUUID().toString(),
            courseName,
            courseCode,
            department,
            batch,
            "16/09/22"))
    }

     fun getTitle(item: Classroom): String {
        return item.courseName
    }

    fun getColor():String{
        idx%=colorList.size
        return colorList[idx++]
    }
}