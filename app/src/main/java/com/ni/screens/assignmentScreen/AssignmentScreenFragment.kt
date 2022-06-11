package com.ni.screens.assignmentScreen

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.core.adapter.AbstractAdapter
import com.ni.core.baseClasses.BaseObservableFragment
import com.ni.dialogs.plagiarismPenaltyDialog.PlagiarismPenaltyDialog
import com.ni.dialogs.plagiarismPenaltyDialog.PlagiarismPenaltyDialogListener
import com.ni.models.SubmitModel
import com.ni.screens.classRoomScreen.ClassRoomFragment
import com.ni.teachersassistant.databinding.AssignmentScreenFragmentBinding
import com.ni.teachersassistant.databinding.SubmitItemLayoutBinding

class AssignmentScreenFragment :
    BaseObservableFragment<AssignmentScreenFragmentBinding, AssignmentScreenListener>(
        AssignmentScreenFragmentBinding::inflate
    ),PlagiarismPenaltyDialogListener {
    companion object {
        const val TAG = "AssignmentScreenFragment"
        fun newInstance(name: String) = AssignmentScreenFragment().apply {

        }
    }

    val viewModel by viewModels<AssignmentScreenViewModel>()
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
            val dialog = PlagiarismPenaltyDialog()
            dialog.show(childFragmentManager, "CreateClassRoomDialog")
        }
    }

    override fun onDialogNegativeClick() {

    }

    override fun onDialogPositiveClick(penaltyAmount: Int) {

    }

}