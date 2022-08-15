package com.ni.data.repository.remote

import com.ni.data.models.AssignmentModel

interface AssignmentRepository {
    fun getAssignment(): AssignmentModel
    fun createAssignment(assignmentModel : AssignmentModel)
    fun deleteAssignment(assignmentModel : AssignmentModel)
    fun updateAssignment(assignmentModel : AssignmentModel)
}