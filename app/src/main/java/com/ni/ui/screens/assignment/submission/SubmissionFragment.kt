package com.ni.ui.screens.assignment.submission

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.data.models.Submit
import com.ni.teachersassistant.databinding.SubmissionFragmentBinding
import com.ni.teachersassistant.databinding.SubmitItemLayoutBinding
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.adapter.AbstractAdapter
import com.ni.ui.common.baseClasses.BaseObservableFragment
import kotlinx.android.synthetic.main.submit_item_layout.*


class SubmissionFragment :
    BaseObservableFragment<SubmissionFragmentBinding, SubmissionListener>(SubmissionFragmentBinding::inflate) {

    companion object {
        const val TAG = "SubmissionFragment"
        const val ASSIGNMENTID = "AssignmentId"
        fun newInstance(assignmentId: String) = SubmissionFragment().apply {
            this.arguments = Bundle().apply {
                putString(ASSIGNMENTID, assignmentId)
            }
        }
    }

    val viewModel by viewModels<SubmissionViewModel>() {
        ViewModelFactory()
    }

    private val submissionListAdapter: AbstractAdapter<Submit, SubmitItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Submit, SubmitItemLayoutBinding>(SubmitItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: SubmitItemLayoutBinding,
                item: Submit,
                position: Int,
            ) {
                itemBinding.tvId.text = item.studentId
                itemBinding.etObtained.setText(item.obtained.toString())
                itemBinding.etBonus.setText(item.bonus.toString())
                itemBinding.etPenalty.setText(item.penalty.toString())
                itemBinding.tvTotal.text = item.total.toString()
                itemBinding.tvAnswerScript.setOnClickListener {
                    openFile(item.answerScriptUrl)
                }
                if (item.checked) {
                    itemBinding.rbYes.isChecked = true
                    itemBinding.rbNo.isChecked = false
                } else {
                    itemBinding.rbYes.isChecked = false
                    itemBinding.rbNo.isChecked = true
                }
            }
        }
    }

    fun openFile(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
        startActivity(intent)
    }

    override fun initView() {
        initUiListener()
        initObservers()
        initGetArguments()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
        initRecycler()
    }

    private fun initRecycler() {
        binding.optionRecyclerViewSLF.adapter = submissionListAdapter
    }


    private fun initObservers() {
        viewModel.isLoading.observe(this) {
            if (viewModel.isLoading.value == true)
                binding.llProgressBar.visibility = View.VISIBLE
            else
                binding.llProgressBar.visibility = View.GONE
        }
        viewModel.assignmentId.observe(this) {
            viewModel.retrieveSubmission()
        }
        viewModel.subissionList.observe(this) {
            submissionListAdapter.setItems(it)
        }
    }

    private fun initGetArguments() {
        viewModel.setAssignmentId(arguments?.getString(ASSIGNMENTID).toString())

    }

    private fun initBackPressed() {
        (requireActivity()).onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popFragment()
                }
            })
    }


    private fun initBtnListener() {}

}