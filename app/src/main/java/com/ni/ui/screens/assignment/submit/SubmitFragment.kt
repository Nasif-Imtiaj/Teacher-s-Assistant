package com.ni.ui.screens.assignment.submit

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.teachersassistant.databinding.SubmitFragmentBinding
import com.ni.ui.common.baseClasses.BaseObservableFragment


class SubmitFragment : BaseObservableFragment<SubmitFragmentBinding,SubmitListener>(SubmitFragmentBinding::inflate) {

    companion object {
        const val TAG = "SubmitFragment"
        fun newInstance() = SubmitFragment()
    }


    val viewModel by viewModels<SubmitViewModel>()
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