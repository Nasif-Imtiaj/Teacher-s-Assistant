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

    val localSubmissionList = ArrayList<Submit>()

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
                    localSubmissionList.addAll(list)
                }

                override fun onFailed() {
                    _isLoading.postValue(false)
                }

            })
        }
    }

    fun setAssignmentId(id: String) {
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
    }

    fun enableUpdate(value: Boolean) {
        _enableUpdate.postValue(value)
    }

    fun updateResultAtIdx(item: Submit, idx: Int) {
        localSubmissionList[idx] = item
    }

    fun updateToDatabase() {
        _isLoading.postValue(true)
        for (i in localSubmissionList) {
            submitRepository.update(i)
        }

        _isLoading.postValue(false)
    }
}