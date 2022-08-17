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

    init {
        _classRoomDataList.postValue(dummyClassRoomList.classList)
        retrieveClassrooms()
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

    fun retrieveClassrooms() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            retrieveClassroomsAsync()
        }
    }

    private fun createClassroom(classroom: Classroom) {
        classroomRepository.create(classroom)
    }

    fun addToClassRoom(courseName: String, department: String, batch: String) {
        createClassroom(Classroom(UUID.randomUUID().toString(),
            courseName,
            department,
            batch,
            "16/09/22"))
    }

    public fun getTitle(item: Classroom): String {
        return item.courseName
    }
}