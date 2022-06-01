package com.ni.dialogs.createClassRoomDialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ni.teachersassistant.R
import kotlinx.android.synthetic.main.create_classroom_dialog_layout.view.*

class CreateClassRoomDialog : DialogFragment() {
    internal lateinit var listener: CreateClassRoomDialogListener

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
                        var dept = view.etDepartment.getText().toString()
                        var sub = view.etSubject.getText().toString()
                        var code = view.etCode.getText().toString()
                        listener.onDialogPositiveClick(dept,sub,code)
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