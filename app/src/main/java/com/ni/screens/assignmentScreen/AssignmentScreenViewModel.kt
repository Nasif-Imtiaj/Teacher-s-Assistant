package com.ni.screens.assignmentScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.models.SubmitModel
import com.ni.repository.SubmissionDataList


class AssignmentScreenViewModel : ViewModel() {
    val assignmentDataList = MutableLiveData<List<SubmitModel>>()
    private val _assignmentDataList = SubmissionDataList
    var selectedIdx = 0

    init {
        assignmentDataList.postValue(_assignmentDataList.submissionDataList)
    }

    fun updateResultData(obtained: Int, bonus: Int, penalty: Int) {
        assignmentDataList.value!!.get(selectedIdx).marks.bonus = bonus
        assignmentDataList.value!!.get(selectedIdx).marks.obtained = obtained
        assignmentDataList.value!!.get(selectedIdx).marks.penalty = penalty
        assignmentDataList.postValue(assignmentDataList.value)
    }

    fun updateSelectedStudent(selectedIdx: Int) {
        this.selectedIdx = selectedIdx
    }
}