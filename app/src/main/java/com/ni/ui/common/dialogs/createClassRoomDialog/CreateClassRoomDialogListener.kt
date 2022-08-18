package com.ni.ui.common.dialogs.createClassRoomDialog

import com.ni.data.models.Classroom

interface CreateClassRoomDialogListener {
    fun onDialogPositiveClick(classroom: Classroom)
    fun onDialogNegativeClick()
}