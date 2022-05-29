package com.ni.screens.homeScreen

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.core.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.HomeScreenFragmentBinding

class HomeScreenFragment :
    BaseObservableFragment<HomeScreenFragmentBinding, HomeScreenListener>(HomeScreenFragmentBinding::inflate) {
    companion object {
        const val TAG = "HomeScreenFragment"
        fun newInstance() = HomeScreenFragment().apply {}
    }

    val viewModel by viewModels<HomeScreenViewModel>()


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
        binding.ivTeacherAva.setOnClickListener {
           notify { it.onTeacherProfileClicked() }
        }
    }


    override fun onStart() {
        super.onStart()
        registerListener()
    }

    private fun registerListener() {
        parentFragment?.let {
            if (it is HomeScreenListener) {
                registerObserver(it)
            }
        }
        if (context is HomeScreenListener) {
            registerObserver(context as HomeScreenListener)
        }
    }

    override fun onStop() {
        super.onStop()
        unRegisterListener()
    }

    private fun unRegisterListener() {
        parentFragment?.let {
            if (it is HomeScreenListener) {
                unRegisterObserver(it)
            }
        }
        if (context is HomeScreenListener) {
            unRegisterObserver(context as HomeScreenListener)
        }
    }
}