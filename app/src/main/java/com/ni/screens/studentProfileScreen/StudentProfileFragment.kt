package com.ni.screens.studentProfileScreen

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.core.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.StudentProfileFragmentBinding

class StudentProfileFragment :
    BaseObservableFragment<StudentProfileFragmentBinding, StudentProfileListener>(
        StudentProfileFragmentBinding::inflate
    ) {

    companion object {
        var TAG = "StudentProfileFragment"
        fun newInstance() = StudentProfileFragment()
    }

    val viewModel by viewModels<StudentProfileViewModel>()


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