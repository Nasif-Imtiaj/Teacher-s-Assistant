package com.ni.data.repository.remote

import com.ni.data.models.Submit

interface SubmitRepository {
    fun create(submit: Submit)
    fun retrieve(assignmentId: String, submitCallbacks: SubmitCallbacks)
    fun update(submit: Submit)
    fun delete(submit: Submit)
}

interface SubmitCallbacks {
    fun onSuccess(list: ArrayList<Submit>)
    fun onFailed()
}