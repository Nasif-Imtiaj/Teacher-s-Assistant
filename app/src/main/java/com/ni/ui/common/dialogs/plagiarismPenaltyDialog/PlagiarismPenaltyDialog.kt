package com.ni.ui.common.dialogs.plagiarismPenaltyDialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ni.teachersassistant.R
import kotlinx.android.synthetic.main.plagiarism_penalty_dialog_layout.view.*

class PlagiarismPenaltyDialog : DialogFragment() {
    companion object {
        const val TAG = "PlagiarismPenaltyDialog"
    }
    lateinit var listener: PlagiarismPenaltyDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragment?.let {
            if (it is PlagiarismPenaltyDialogListener) {
                listener = it
            }
        }
        if (context is PlagiarismPenaltyDialogListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            var view = inflater.inflate(R.layout.plagiarism_penalty_dialog_layout, null)
            builder.setView(view)
                .setPositiveButton("Done",
                    DialogInterface.OnClickListener { dialog, id ->
                        var value = view.etPenaltyMark.text

                        listener.onDialogPositiveClick((value.toString()).toInt())
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}