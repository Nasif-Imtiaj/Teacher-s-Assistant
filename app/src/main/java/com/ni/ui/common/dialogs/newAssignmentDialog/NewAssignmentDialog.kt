package com.ni.ui.common.dialogs.newAssignmentDialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ni.data.models.Assignment
import com.ni.teachersassistant.R
import kotlinx.android.synthetic.main.new_assignment_dialog_layout.*
import kotlinx.android.synthetic.main.new_assignment_dialog_layout.view.*
import kotlinx.android.synthetic.main.new_student_dialog_layout.etName
import kotlinx.android.synthetic.main.new_student_dialog_layout.view.*
import kotlinx.android.synthetic.main.new_student_dialog_layout.view.etName
import java.util.*

class NewAssignmentDialog : DialogFragment() {
    companion object {
        const val TAG = "NewAssignmentDialog"
        fun newInstance(roomId: String) = NewAssignmentDialog().apply {
            this.roomId = roomId
        }
    }

    var roomId = ""
    lateinit var listener: NewAssignmentDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragment?.let {
            if (it is NewAssignmentDialogListener) {
                listener = it
            }
        }
        if (context is NewAssignmentDialogListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            var view = inflater.inflate(R.layout.new_assignment_dialog_layout, null)
            builder.setView(view)
                .setPositiveButton("Done",
                    DialogInterface.OnClickListener { dialog, id ->
                        var assignment = Assignment(
                            UUID.randomUUID().toString(),
                            roomId,
                            view.etName.text.toString(),
                            view.etStartDate.text.toString(),
                            view.etEndDate.text.toString(),
                            view.etMarks.text.toString().toInt()
                        )
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogNegativeClick()
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}