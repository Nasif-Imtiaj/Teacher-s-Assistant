package com.ni.data.repository.remote

import com.ni.data.models.Submission

interface SubmissionRepository {
    fun create(submission: Submission)
    fun retrieve(submission: Submission)
    fun update(submission: Submission)
    fun delete(submission: Submission)
}