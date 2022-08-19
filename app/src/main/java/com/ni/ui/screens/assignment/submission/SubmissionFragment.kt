package com.ni.ui.screens.assignment.submission

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.SubmissionFragmentBinding
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.ui.screens.assignment.submit.SubmitFragment
import com.ni.ui.screens.assignment.submit.SubmitViewModel

class SubmissionFragment :
    BaseObservableFragment<SubmissionFragmentBinding, SubmissionListener>(SubmissionFragmentBinding::inflate) {

    companion object {
        const val TAG = "SubmissionFragment"
        fun newInstance() = SubmissionFragment()
    }


    val viewModel by viewModels<SubmissionViewModel>()
    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
        initRecycler()
    }

    private fun initObservers() {}

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