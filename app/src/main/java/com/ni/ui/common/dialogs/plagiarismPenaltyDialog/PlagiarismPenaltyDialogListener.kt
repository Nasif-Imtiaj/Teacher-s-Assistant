package com.ni.ui.common.dialogs.plagiarismPenaltyDialog

interface PlagiarismPenaltyDialogListener {
    fun onDialogPositiveClick(penaltyAmount:Int)
    fun onDialogNegativeClick()
}