package com.ni.ui.screens.assignment

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.data.models.Assignment
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.AssignmentScreenFragmentBinding
import com.ni.ui.common.ViewModelFactory

class AssignmentFragment :
    BaseObservableFragment<AssignmentScreenFragmentBinding, AssignmentListener>(
        AssignmentScreenFragmentBinding::inflate
    )  {
    companion object {
        const val TAG = "AssignmentScreenFragment"
        const val ASSIGNMENTNAME = "AssignmentName"
        fun newInstance(assignment: Assignment) = AssignmentFragment().apply {
            this.arguments = Bundle().apply {
                putString(ASSIGNMENTNAME, assignment.name)
            }
        }
    }

    val viewModel by viewModels<AssignmentViewModel>()


    override fun initView() {
        initUiListener()
        initObservers()
        initGetArguments()
        initSetupView()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
        initRecycler()
    }

    private fun initObservers() {}

    private fun initGetArguments() {
        viewModel.assignmentName = arguments?.getString(ASSIGNMENTNAME).toString()
    }

    private fun initSetupView() {
        binding.tvAssignmentName.text = viewModel.assignmentName
    }

    private fun initBackPressed() {
        (requireActivity()).onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popFragment()
                }
            })
    }

    private fun initRecycler() {}

    private fun initBtnListener() {}
}