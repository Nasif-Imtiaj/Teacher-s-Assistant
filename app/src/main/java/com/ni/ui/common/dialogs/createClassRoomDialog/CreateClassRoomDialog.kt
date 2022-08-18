package com.ni.ui.common.dialogs.createClassRoomDialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.ni.data.models.Classroom
import com.ni.teachersassistant.R
import kotlinx.android.synthetic.main.create_classroom_dialog_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class CreateClassRoomDialog : DialogFragment() {
    companion object {
        const val TAG = "CreateClassRoomDialog"
    }

    lateinit var listener: CreateClassRoomDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragment?.let {
            if (it is CreateClassRoomDialogListener) {
                listener = it
            }
        }
        if (context is CreateClassRoomDialogListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            var view = inflater.inflate(R.layout.create_classroom_dialog_layout, null)
            builder.setView(view)
                .setPositiveButton("Done",
                    DialogInterface.OnClickListener { dialog, id ->
                        var courseName = view.etCourseName.text.toString()
                        var courseCode = view.etCourseCode.text.toString()
                        var department = view.etDepartment.text.toString()
                        var batch = view.etBatch.text.toString()
                        var section = view.etSection.text.toString()
                        listener.onDialogPositiveClick(
                            Classroom(
                                UUID.randomUUID().toString(),
                                FirebaseAuth.getInstance().currentUser!!.uid,
                                courseName,
                                courseCode,
                                department,
                                batch,
                                section,
                                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                            )
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