package com.ni.ui.screens.classRoom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ni.data.models.Assignment
import com.ni.data.models.Enrollment
import com.ni.data.models.Student
import com.ni.data.repository.remote.*
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
    private val _enrolledStudentList = MutableLiveData<ArrayList<String>>()
    val enrolledStudentList: LiveData<ArrayList<String>>
        get() = _enrolledStudentList

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

    private suspend fun retrieveAssignmentAsync() {
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

    fun retrieveStudent() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            retrieveStudentAsync()
        }
    }

    private suspend fun retrieveStudentAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            enrolledStudentList.value?.let {
                studentRepository.retrieve(it, object : StudentCallbacks {
                    override fun onSuccess(list: ArrayList<Student>) {
                        _studentDataList.postValue(list)
                        _isLoading.postValue(false)
                    }

                    override fun onFailed() {
                        _isLoading.postValue(false)
                    }
                })
            }
        }
    }

    fun createEnrollment(enrollment: Enrollment) {
        enrollmentRepository.create(enrollment)
    }

    fun retrieveEnrollment() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            retrieveEnrollmentAsync()
        }
    }

    private suspend fun retrieveEnrollmentAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            enrollmentRepository.retrieve(classroomId, object : EnrollmentCallbacks {
                override fun onSuccess(list: ArrayList<Enrollment>) {
                    _enrollmentDataList.postValue(list)
                    _isLoading.postValue(false)
                }

                override fun onFailed() {
                    _isLoading.postValue(false)
                }

            })
        }
    }

    fun generateEnrolledStudentList() {
        val list = ArrayList<String>()
        for (i in enrollmentDataList.value!!) {
            list.add(i.studentId)
        }
        _enrolledStudentList.postValue(list)
    }
}