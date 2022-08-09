package com.ni.ui.screens.teacherProfileScreen

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.TeacherProfileFragmentBinding

class TeacherProfileFragment : BaseObservableFragment<TeacherProfileFragmentBinding,TeacherProfileListener>(TeacherProfileFragmentBinding::inflate) {

    companion object {
        const val TAG = "TeacherProfileFragment"
        fun newInstance() = TeacherProfileFragment().apply {}
    }

    val viewModel by viewModels<TeacherProfileViewModel>()


    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
    }
    private fun initObservers(){

    }
    private fun initBackPressed() {
        (requireActivity()).onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popFragment()
                }
            })
    }

    private fun initBtnListener(){
        binding.ivBackBtn.setOnClickListener {
            popFragment()
        }
    }


}