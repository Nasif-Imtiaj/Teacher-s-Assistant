package com.ni.ui.screens.result

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.data.models.Marks
import com.ni.teachersassistant.databinding.FragmentMarksBinding
import com.ni.teachersassistant.databinding.MarksItemLayoutBinding
import com.ni.ui.common.adapter.AbstractAdapter
import com.ni.ui.common.baseClasses.BaseObservableFragment

class MarksFragment :
    BaseObservableFragment<FragmentMarksBinding, MarksListener>(FragmentMarksBinding::inflate) {

    companion object {
        const val TAG = "ResultFragment"
        const val ASSIGNMENTID = "AssignmentId"
        const val ASSIGNMENTNAME = "AssignmentName"
        const val BATCH = "Batch"
        fun newInstance(assignmentId: String, assignmentName: String, batch: String) =
            MarksFragment().apply {
                this.arguments = Bundle().apply {
                    putString(ASSIGNMENTID, assignmentId)
                    putString(ASSIGNMENTNAME, assignmentName)
                    putString(BATCH, batch)
                }
            }
    }

    private val marksListAdapter: AbstractAdapter<Marks, MarksItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Marks, MarksItemLayoutBinding>(MarksItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: MarksItemLayoutBinding,
                item: Marks,
                position: Int,
            ) {
                itemBinding.tvStudentId.text = item.studentId
                itemBinding.tvMarks.text = item.obtainedMarks.toString()
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
        binding.tvBatch.text = "Batch : " + viewModel.batch
    }

    fun initBtnListener() {}

    fun initRecycler() {
        binding.optionRecyclerViewRSF.adapter = marksListAdapter
    }

    fun initObservers() {
        viewModel.marksList.observe(this) {
            marksListAdapter.setItems(it)
        }
    }

    private fun initGetArguments() {
        viewModel.assignmentId = arguments?.getString(ASSIGNMENTID).toString()
        viewModel.assignmentName = arguments?.getString(ASSIGNMENTNAME).toString()
        viewModel.batch = arguments?.getString(BATCH).toString()
        viewModel.generateMarksList()
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