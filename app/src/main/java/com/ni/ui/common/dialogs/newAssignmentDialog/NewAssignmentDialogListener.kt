package com.ni.ui.common.dialogs.newAssignmentDialog

import com.ni.data.models.Assignment

interface NewAssignmentDialogListener {
    fun onDialogPositiveClick(assignment: Assignment)
    fun onDialogNegativeClick()
}