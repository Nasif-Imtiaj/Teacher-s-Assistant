package com.ni.data.repository.remote

import com.ni.data.models.Assignment

interface AssignmentRepository {
    fun retrieve()
    fun create(assignment: Assignment)
    fun delete(assignment: Assignment)
    fun update(assignment: Assignment)
}