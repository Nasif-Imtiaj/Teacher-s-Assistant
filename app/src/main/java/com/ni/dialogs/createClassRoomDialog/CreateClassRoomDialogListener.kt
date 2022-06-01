package com.ni.dialogs.createClassRoomDialog

import androidx.fragment.app.DialogFragment

interface CreateClassRoomDialogListener {
    fun onDialogPositiveClick(dept: String, sub: String, code: String)
    fun onDialogNegativeClick()
}