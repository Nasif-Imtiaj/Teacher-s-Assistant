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
import kotlin.math.max
import kotlin.math.min

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

    fun levenshtein(lhs : CharSequence, rhs : CharSequence) : Int {
        val lhsLength = lhs.length
        val rhsLength = rhs.length

        var cost = IntArray(lhsLength + 1) { it }
        var newCost = IntArray(lhsLength + 1) { 0 }

        for (i in 1..rhsLength) {
            newCost[0] = i

            for (j in 1..lhsLength) {
                val editCost= if(lhs[j - 1] == rhs[i - 1]) 0 else 1

                val costReplace = cost[j - 1] + editCost
                val costInsert = cost[j] + 1
                val costDelete = newCost[j - 1] + 1

                newCost[j] = minOf(costInsert, costDelete, costReplace)
            }

            val swap = cost
            cost = newCost
            newCost = swap
        }

        return cost[lhsLength]
    }


    fun startPlagiarismCheck(){
        _isLoading.postValue(true)
        for(i in localSubmissionList){
            var penalty = 0f
            for(j in localSubmissionList){
                if(i==j)continue
                var req = levenshtein(i.solutionTxt,j.solutionTxt)*1.0f
                var mn = min(i.solutionTxt.length,j.solutionTxt.length)*1.0f
                var cur = 100f-(100f*(req/mn))
                penalty = max(penalty,cur)
            }
            penalty = min(penalty,100f)
            i.penalty = penalty
            i.total = i.obtained+i.bonus-i.penalty
            i.total = max(0f,i.total)
            i.total = min(100f,i.total)
        }
        _submissionsList.postValue(localSubmissionList)
        _isLoading.postValue(false)
        _enableUpdate.postValue(true)
    }
}