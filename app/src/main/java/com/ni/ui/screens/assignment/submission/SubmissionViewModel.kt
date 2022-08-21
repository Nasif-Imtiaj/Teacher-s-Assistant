package com.ni.ui.screens.assignment.submission


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ni.data.models.Marks
import com.ni.data.models.Submit
import com.ni.data.repository.remote.SubmitCallbacks
import com.ni.data.repository.remote.SubmitRepository
import com.ni.ui.activity.avmMarksData
import com.ni.ui.common.baseClasses.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubmissionViewModel(private val submitRepository: SubmitRepository) : BaseViewModel() {
    val _submissionsList = MutableLiveData<ArrayList<Submit>>()
    val subissionList: LiveData<ArrayList<Submit>>
        get() = _submissionsList
    val _assignmentId = MutableLiveData<String>()
    val assignmentId: LiveData<String>
        get() = _assignmentId
    val _enableUpdate = MutableLiveData<Boolean>()
    val enableUpdate: LiveData<Boolean>
        get() = _enableUpdate

    fun retrieveSubmission() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            retrieveSubmissionAsync()
        }
    }

    private suspend fun retrieveSubmissionAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            submitRepository.retrieve(assignmentId.value!!, object : SubmitCallbacks {
                override fun onSuccess(list: ArrayList<Submit>) {
                    _isLoading.postValue(false)
                    _submissionsList.postValue(list)
                    Log.d("TESTID", "initGetArguments:4  ${list.size}")
                }

                override fun onFailed() {
                    _isLoading.postValue(false)
                }

            })
        }
    }

    fun setAssignmentId(id: String) {
        Log.d("TESTID", "initGetArguments:2  ${id}")
        _assignmentId.postValue(id)
    }

    fun addToMarksData() {
        if (subissionList.value.isNullOrEmpty()) return
        for (i in subissionList.value!!) {
            for (j in avmMarksData) {
                if (i.id == j.id) {
                    avmMarksData.remove(j)
                    break
                }
            }
        }
        for (i in subissionList.value!!) {
            avmMarksData.add(
                Marks(
                    i.id,
                    "classroomId",
                    assignmentId.value!!,
                    i.studentId,
                    i.total
                )
            )
        }
        Log.d("TESTAVMMARKSDATA", "addToMarksData: ${avmMarksData.size}")
    }

    fun enableUpdate(value :Boolean){
        _enableUpdate.postValue(value)
    }

}