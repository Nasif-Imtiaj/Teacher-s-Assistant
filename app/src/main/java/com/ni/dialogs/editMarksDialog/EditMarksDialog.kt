package com.ni.dialogs.editMarksDialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ni.models.SubmitModel
import com.ni.teachersassistant.R
import kotlinx.android.synthetic.main.create_classroom_dialog_layout.view.*
import kotlinx.android.synthetic.main.edit_marks_dialog_layout.view.*
import kotlinx.android.synthetic.main.submit_item_layout.view.*

class EditMarksDialog : DialogFragment() {
    companion object {
        const val KEY_OBTAINED= "obtained"
        const val KEY_BONUS= "bonus"
        const val KEY_PENALTY= "penalty"
        const val TAG = "EditMarksDialog"
    }

    lateinit var listener: EditMarksDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        super.onAttach(context)
        parentFragment?.let {
            if (it is EditMarksDialogListener) {
                listener = it
            }
        }
        if (context is EditMarksDialogListener) {
            listener = context
        }
    }
    fun newInstance(item: SubmitModel): EditMarksDialog? {
        val fragment = EditMarksDialog()
        val bundle = Bundle()
        bundle.putInt(KEY_OBTAINED, item.marks.obtained)
        bundle.putInt(KEY_BONUS, item.marks.bonus)
        bundle.putInt(KEY_PENALTY, item.marks.penalty)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            var view = inflater.inflate(R.layout.edit_marks_dialog_layout, null)
            view.etObtained.setText(arguments?.get(KEY_OBTAINED).toString())
            view.etBonus.setText(arguments?.get(KEY_BONUS).toString())
            view.etPenalty.setText(arguments?.get(KEY_PENALTY).toString())
            builder.setView(view)
                .setPositiveButton("Done",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onUpdatedMarks(
                            view.etObtained.text.toString().toInt(),
                            view.etBonus.text.toString().toInt(),
                            view.etPenalty.text.toString().toInt()
                        )
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}