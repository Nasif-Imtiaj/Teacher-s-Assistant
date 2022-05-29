package com.ni.screens.classRoomScreen

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
import com.ni.teachersassistant.databinding.ClassRoomFragmentBinding

class ClassRoomFragment :
    BaseObservableFragment<ClassRoomFragmentBinding, ClassRoomListener>(ClassRoomFragmentBinding::inflate) {

    companion object {
        const val TAG = "ClassRoomFragment"
        fun newInstance() = ClassRoomFragment().apply {}
    }

    val viewModel by viewModels<ClassRoomViewModel>()


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