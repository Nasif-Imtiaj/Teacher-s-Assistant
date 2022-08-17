package com.ni.ui.common.dialogs.newStudentDialog

import com.ni.data.models.Student

interface NewStudentDialogListener {
    fun onDialogPositiveClick(student: Student)
    fun onDialogNegativeClick()
}