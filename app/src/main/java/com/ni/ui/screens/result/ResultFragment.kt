package com.ni.ui.screens.result

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.teachersassistant.databinding.FragmentResultBinding
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.baseClasses.BaseObservableFragment

class ResultFragment :
    BaseObservableFragment<FragmentResultBinding, ResultListener>(FragmentResultBinding::inflate) {

    companion object {
        const val TAG = "ResultFragment"
        const val ASSIGNMENTNAME = "AssignmentName"
        const val BATCH = "Batch"
        fun newInstance(assignmentName: String, batch: String) = ResultFragment().apply {
            this.arguments = Bundle().apply {
                putString(ASSIGNMENTNAME, assignmentName)
                putString(BATCH, batch)
            }
        }
    }

    val viewModel by viewModels<ResultViewModel>()

    override fun initView() {
        initUiListener()
        initObservers()
        initGetArguments()
        initSetupUi()
        initBackPressed()
    }


    private fun initUiListener() {
        initBtnListener()
        initRecycler()
    }

    private fun initSetupUi() {
        binding.tvAssignmentName.text = viewModel.assignmentName
        binding.tvBatch.text  = "Batch : " +  viewModel.batch
    }
    fun initBtnListener(){}

    fun initRecycler(){}

    fun initObservers() {}

    private fun initGetArguments() {
        viewModel.assignmentName = arguments?.getString(ASSIGNMENTNAME).toString()
        viewModel.batch = arguments?.getString(BATCH).toString()
    }

    private fun initBackPressed() {
        (requireActivity()).onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popFragment()
                }
            })
    }
}