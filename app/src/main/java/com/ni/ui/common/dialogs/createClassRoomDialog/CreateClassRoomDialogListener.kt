package com.ni.ui.common.dialogs.createClassRoomDialog

interface CreateClassRoomDialogListener {
    fun onDialogPositiveClick(dept: String, sub: String, code: String)
    fun onDialogNegativeClick()
}