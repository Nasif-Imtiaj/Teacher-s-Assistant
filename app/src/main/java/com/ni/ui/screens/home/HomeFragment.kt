package com.ni.ui.screens.home

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.HomeScreenFragmentBinding

class HomeFragment :
    BaseObservableFragment<HomeScreenFragmentBinding, HomeListener>(HomeScreenFragmentBinding::inflate) {
    companion object {
        const val TAG = "HomeScreenFragment"
        fun newInstance() = HomeFragment().apply {}
    }

    val viewModel by viewModels<HomeViewModel>()


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
        binding.cvClassroom.setOnClickListener {
            notify { it.onClassRoomClicked() }
        }
        binding.cvLibrary.setOnClickListener{
            notify { it.onLibraryClicked() }
        }
    }


    override fun onStart() {
        super.onStart()
        registerListener()
    }

    private fun registerListener() {
        parentFragment?.let {
            if (it is HomeListener) {
                registerObserver(it)
            }
        }
        if (context is HomeListener) {
            registerObserver(context as HomeListener)
        }
    }

    override fun onStop() {
        super.onStop()
        unRegisterListener()
    }

    private fun unRegisterListener() {
        parentFragment?.let {
            if (it is HomeListener) {
                unRegisterObserver(it)
            }
        }
        if (context is HomeListener) {
            unRegisterObserver(context as HomeListener)
        }
    }
}