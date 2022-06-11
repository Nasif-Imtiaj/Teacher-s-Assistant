package com.ni.dialogs.plagiarismPenaltyDialog

interface PlagiarismPenaltyDialogListener {
    fun onDialogPositiveClick(penaltyAmount:Int)
    fun onDialogNegativeClick()
}