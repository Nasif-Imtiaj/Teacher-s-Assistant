package com.ni.ui.common.dialogs.newStudentDialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ni.data.models.Student
import com.ni.teachersassistant.R
import kotlinx.android.synthetic.main.create_classroom_dialog_layout.view.*
import kotlinx.android.synthetic.main.new_student_dialog_layout.*
import java.util.*


class NewStudentDialog : DialogFragment() {
    companion object {
        const val TAG = "NewStudentDialog"
    }

    lateinit var listener: NewStudentDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragment?.let {
            if (it is NewStudentDialogListener) {
                listener = it
            }
        }
        if (context is NewStudentDialogListener) {
            listener = context
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            var view = inflater.inflate(R.layout.new_student_dialog_layout, null)
            builder.setView(view)
                .setPositiveButton("Done",
                    DialogInterface.OnClickListener { dialog, id ->
                        var student = Student(
                            UUID.randomUUID().toString(),
                            etName.text.toString(),
                            etStudentId.text.toString(),
                            etDepartment.text.toString(),
                            etBatch.text.toString(),
                            etSection.text.toString(),
                            "https://firebasestorage.googleapis.com/v0/b/ni-teacher-assistant.appspot.com/o/profilePic%2Fp1.jpeg?alt=media&token=0592c9d4-3861-4809-adf3-7663815c5617"
                        )
                        listener.onDialogPositiveClick(student )
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