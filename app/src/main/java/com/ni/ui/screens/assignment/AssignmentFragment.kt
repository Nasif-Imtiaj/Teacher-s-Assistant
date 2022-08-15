package com.ni.ui.screens.assignment

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.ui.common.adapter.AbstractAdapter
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.ui.common.dialogs.editMarksDialog.EditMarksDialog
import com.ni.ui.common.dialogs.editMarksDialog.EditMarksDialogListener
import com.ni.ui.common.dialogs.plagiarismPenaltyDialog.PlagiarismPenaltyDialog
import com.ni.ui.common.dialogs.plagiarismPenaltyDialog.PlagiarismPenaltyDialogListener
import com.ni.data.models.SubmitModel
import com.ni.ui.screens.classRoom.ClassRoomFragment
import com.ni.teachersassistant.databinding.AssignmentScreenFragmentBinding
import com.ni.teachersassistant.databinding.SubmitItemLayoutBinding

class AssignmentFragment :
    BaseObservableFragment<AssignmentScreenFragmentBinding, AssignmentListener>(
        AssignmentScreenFragmentBinding::inflate
    ), PlagiarismPenaltyDialogListener, EditMarksDialogListener {
    companion object {
        const val TAG = "AssignmentScreenFragment"
        fun newInstance(name: String) = AssignmentFragment().apply {

        }
    }

    val viewModel by viewModels<AssignmentViewModel>()
    private val submitListAdapter: AbstractAdapter<SubmitModel, SubmitItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<SubmitModel, SubmitItemLayoutBinding>(SubmitItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: SubmitItemLayoutBinding,
                item: SubmitModel,
                position: Int
            ) {
                Log.d(ClassRoomFragment.TAG, "bind: ")
                itemBinding.tvId.text = item.studentID
                itemBinding.tvExamValue.text = item.marks.examMarks.toString()
                itemBinding.tvObtainedValue.text = item.marks.obtained.toString()
                itemBinding.tvBonusValue.text = item.marks.bonus.toString()
                itemBinding.tvPenaltyValue.text = item.marks.penalty.toString()
                itemBinding.tvTotalValue.text = item.marks.total.toString()
                itemBinding.ivEditMarks.setOnClickListener {
                    viewModel.updateSelectedStudent(position)
                    loadEditMarksDialog(item)
                }
            }
        }
    }


    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
        initRecycler()
    }

    private fun initObservers() {
        viewModel.assignmentDataList.observe(this) {
            submitListAdapter.setItems(it)
        }
    }

    private fun initBackPressed() {
        (requireActivity()).onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popFragment()
                }
            })
    }

    private fun initRecycler() {
        binding.optionRecyclerViewASF.adapter = submitListAdapter
    }

    private fun initBtnListener() {
        binding.tvCalculate.setOnClickListener {
            loadPlagiarismPenaltyDialog()
        }
    }

    private fun loadPlagiarismPenaltyDialog() {
        val dialog = PlagiarismPenaltyDialog()
        dialog.show(childFragmentManager, PlagiarismPenaltyDialog.TAG)
    }

    private fun loadEditMarksDialog(item: SubmitModel) {
        EditMarksDialog().newInstance(item)?.show(childFragmentManager, EditMarksDialog.TAG)
    }

    override fun onDialogNegativeClick() {

    }

    override fun onDialogPositiveClick(penaltyAmount: Int) {

    }

    override fun onUpdatedMarks(obtained: Int, bonus: Int, penalty: Int) {
        Log.d(TAG, "onUpdatedMarks: $obtained $bonus $penalty")
        viewModel.updateResultData(obtained, bonus, penalty)
    }

}