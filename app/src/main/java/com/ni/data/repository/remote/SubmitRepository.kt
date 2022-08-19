package com.ni.data.repository.remote

import com.ni.data.models.Submit

interface SubmitRepository {
    fun create(submit: Submit)
    fun retrieve(submit: Submit)
    fun update(submit: Submit)
    fun delete(submit: Submit)
}

interface SubmitCallbacks {
    fun onSuccess()
    fun onFailed()
}