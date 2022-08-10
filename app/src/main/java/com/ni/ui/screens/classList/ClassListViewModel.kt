package com.ni.ui.screens.classList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.ClassListModel
import com.ni.data.repository.ClassListDataList

class ClassListViewModel : ViewModel() {
    private val _classRoomList = ClassListDataList
    val classRoomDataList = MutableLiveData<List<ClassListModel>>()

    init {
        classRoomDataList.postValue(_classRoomList.classList)
    }

    fun addToClassRoom(dept: String, sub: String, code: String) {
        _classRoomList.classList.add(
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
        classRoomDataList.postValue(_classRoomList.classList)
    }

    public fun getTitle(item: ClassListModel): String {
        return item.dept.toString() + " " + item.subjectCode + " : " + item.subject
    }
}