package com.ni.ui.screens.classRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ni.data.models.Assignment
import com.ni.data.models.Enrollment
import com.ni.data.models.Student
import com.ni.data.repository.remote.AssignmentCallbacks
import com.ni.data.repository.remote.AssignmentRepository
import com.ni.data.repository.remote.EnrollmentRepository
import com.ni.data.repository.remote.StudentRepository
import com.ni.ui.common.baseClasses.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClassRoomViewModel(
    private val enrollmentRepository: EnrollmentRepository,
    private val studentRepository: StudentRepository,
    private val assignmentRepository: AssignmentRepository,
) : BaseViewModel() {

    private val _enrollmentDataList = MutableLiveData<ArrayList<Enrollment>>()
    val enrollmentDataList: LiveData<ArrayList<Enrollment>>
        get() = _enrollmentDataList
    private val _studentDataList = MutableLiveData<ArrayList<Student>>()
    val studentDataList: LiveData<ArrayList<Student>>
        get() = _studentDataList
    private val _assignmentDataList = MutableLiveData<ArrayList<Assignment>>()
    val assignmentDataList: LiveData<ArrayList<Assignment>>
        get() = _assignmentDataList
    var classroomId = ""

    init {

    }


    fun createAssignment(assignment: Assignment) {
        assignmentRepository.create(assignment)
    }

    fun retrieveAssignment() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            retrieveAssignmentAsync()
        }

    }

    suspend fun retrieveAssignmentAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            assignmentRepository.retrieve(classroomId, object : AssignmentCallbacks {
                override fun onSuccess(list: ArrayList<Assignment>) {
                    _isLoading.postValue(false)
                    _assignmentDataList.postValue(list)
                }

                override fun onFailed() {
                    _isLoading.postValue(false)
                }

            })
        }
    }

    fun createStudent(student: Student) {
        studentRepository.create(student)
    }


    fun createEnrollment(enrollment: Enrollment) {
        enrollmentRepository.create(enrollment)
    }

    fun retrieveEnrollment(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            retrieveEnrollmentAsync()
        }
    }

    suspend fun retrieveEnrollmentAsync(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }


}