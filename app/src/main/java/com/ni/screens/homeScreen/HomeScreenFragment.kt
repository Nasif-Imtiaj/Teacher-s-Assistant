package com.ni.screens.homeScreen


import androidx.fragment.app.viewModels
import com.ni.core.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.HomeScreenFragmentBinding

class HomeScreenFragment : BaseObservableFragment<HomeScreenFragmentBinding,HomeScreenListener>(HomeScreenFragmentBinding::inflate) {
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

    }
    private fun initObservers(){

    }
    private fun initBackPressed(){

    }


}