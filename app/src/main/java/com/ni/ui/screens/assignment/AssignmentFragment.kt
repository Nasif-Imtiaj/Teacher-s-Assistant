package com.ni.ui.screens.assignment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ni.data.models.Assignment
import com.ni.teachersassistant.R
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.AssignmentScreenFragmentBinding
import com.ni.ui.activity.avmUserType
import com.ni.ui.activity.userIsStudent
import com.ni.ui.screens.assignment.submission.SubmissionFragment
import com.ni.ui.screens.assignment.submit.SubmitFragment
import kotlinx.android.synthetic.main.assignment_screen_fragment.*

class AssignmentFragment :
    BaseObservableFragment<AssignmentScreenFragmentBinding, AssignmentListener>(
        AssignmentScreenFragmentBinding::inflate
    ) {
    companion object {
        const val TAG = "AssignmentScreenFragment"
        const val ASSIGNMENTNAME = "AssignmentName"
        const val ASSIGNMENTID = "AssignmentId"
        fun newInstance(assignment: Assignment) = AssignmentFragment().apply {
            this.arguments = Bundle().apply {
                putString(ASSIGNMENTNAME, assignment.name)
                putString(ASSIGNMENTID, assignment.id)
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
        viewModel.assignmentId = arguments?.getString(ASSIGNMENTID).toString()
        Log.d("TESTID", "initGetArguments:1 ${viewModel.assignmentId}")
    }

    private fun initSetupView() {
        binding.tvAssignmentName.text = viewModel.assignmentName
        if (avmUserType == userIsStudent)
            binding.tvOption2.text = "Submit"
        else
            binding.tvOption2.text = "Submissions"
    }

    private fun initRecycler() {}

    private fun initBtnListener() {
        binding.tvOption1.setOnClickListener {
            binding.tvOption1.setBackgroundResource(R.drawable.text_rounded_selected)
            binding.tvOption2.setBackgroundResource(R.drawable.text_rounded_unselected)
            binding.flAssignmentContainer.visibility = View.GONE
        }
        binding.tvOption2.setOnClickListener {
            binding.tvOption1.setBackgroundResource(R.drawable.text_rounded_unselected)
            binding.tvOption2.setBackgroundResource(R.drawable.text_rounded_selected)
            binding.flAssignmentContainer.visibility = View.VISIBLE
            if (!viewModel.isSubFragmentLoaded) {
                viewModel.isSubFragmentLoaded = true
                if (avmUserType == userIsStudent)
                    loadSubmitScreen()
                else
                    loadSubmissionScreen()
            }
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

    private fun loadSubmissionScreen() {
        loadSubFragment(
            SubmissionFragment.newInstance(
                viewModel.assignmentId
            ),
            flAssignmentContainer.id,
            SubmissionFragment.TAG
        )
    }

    private fun loadSubmitScreen() {
        loadSubFragment(
            SubmitFragment.newInstance(
                viewModel.assignmentId,
                viewModel.assignmentName
            ), flAssignmentContainer.id, SubmitFragment.TAG
        )
    }

    private fun loadSubFragment(
        newFragment: Fragment,
        containerId: Int,
        fragmentTag: String,
    ) {
        try {
            childFragmentManager.beginTransaction()
                .replace(containerId, newFragment, fragmentTag)
                .addToBackStack(fragmentTag)

                .commitAllowingStateLoss()
        } catch (ex: Exception) {
            //Toaster.debugToast(this, "Fragment transaction failed 70 ${ex.message}")
        }
    }
}