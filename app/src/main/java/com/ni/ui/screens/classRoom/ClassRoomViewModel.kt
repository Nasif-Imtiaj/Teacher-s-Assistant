package com.ni.ui.screens.classRoom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.Assignment
import com.ni.data.models.Classroom
import com.ni.data.models.Student
import com.ni.data.repository.dummy.AssignmentDataList
import com.ni.data.repository.dummy.StudentDataList
import com.ni.data.repository.remote.AssignmentRepository
import com.ni.data.repository.remote.ClassroomRepository
import com.ni.ui.common.baseClasses.BaseViewModel

class ClassRoomViewModel(private val assignmentRepository: AssignmentRepository) : BaseViewModel() {
    private val _studentDataList = MutableLiveData<ArrayList<Student>>()
    val studentDataList: LiveData<ArrayList<Student>>
        get() = _studentDataList
    private val _assignmentDataList = MutableLiveData<ArrayList<Assignment>>()
    val assignmentDataList: LiveData<ArrayList<Assignment>>
        get() = _assignmentDataList

    fun createAssignment(assignment: Assignment){
        assignmentRepository.create(assignment)
    }

    init {

    }


}