package com.ni.screens.assignmentScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.core.baseClasses.BaseObservableFragment
import com.ni.screens.teacherProfileScreen.TeacherProfileFragment
import com.ni.screens.teacherProfileScreen.TeacherProfileViewModel
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.AssignmentScreenFragmentBinding

class AssignmentScreenFragment :
    BaseObservableFragment<AssignmentScreenFragmentBinding, AssignmentScreenListener>(
        AssignmentScreenFragmentBinding::inflate
    ) {
    companion object {
        const val TAG = "AssignmentScreenFragment"
        fun newInstance() = AssignmentScreenFragment().apply {}
    }

    val viewModel by viewModels<AssignmentScreenViewModel>()


    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
    }

    private fun initObservers() {

    }

    private fun initBackPressed() {
        (requireActivity()).onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popFragment()
                }
            })
    }

    private fun initBtnListener() {

    }


}