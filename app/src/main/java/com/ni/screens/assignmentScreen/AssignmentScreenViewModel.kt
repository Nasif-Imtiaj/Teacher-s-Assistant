package com.ni.screens.assignmentScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.models.SubmitModel
import com.ni.repository.SubmissionDataList


class AssignmentScreenViewModel : ViewModel() {
    val assignmentDataList = MutableLiveData<List<SubmitModel>>()
    private val _assignmentDataList = SubmissionDataList
    init {
        assignmentDataList.postValue(_assignmentDataList.submissionDataList)
    }
}