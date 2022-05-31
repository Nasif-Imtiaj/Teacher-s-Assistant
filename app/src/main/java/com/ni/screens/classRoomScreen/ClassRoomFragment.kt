package com.ni.screens.classRoomScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.ni.core.adapter.AbstractAdapter
import com.ni.core.adapter.GenericRecyclerAdapter
import com.ni.core.adapter.GenericSimpleRecyclerBindingInterface
import com.ni.core.baseClasses.BaseObservableFragment
import com.ni.models.ClassRoomModel
import com.ni.screens.teacherProfileScreen.TeacherProfileFragment
import com.ni.screens.teacherProfileScreen.TeacherProfileViewModel
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.ClassRoomFragmentBinding
import com.ni.teachersassistant.databinding.ClassroomItemLayoutBinding
import com.ni.teachersassistant.databinding.MainActivityLayoutBinding

class ClassRoomFragment :
    BaseObservableFragment<ClassRoomFragmentBinding, ClassRoomListener>(ClassRoomFragmentBinding::inflate) {
    companion object {
        const val TAG = "ClassRoomFragment"
        fun newInstance() = ClassRoomFragment().apply {}
    }

    val viewModel by viewModels<ClassRoomViewModel>()

    private val adapter: AbstractAdapter<ClassRoomModel, ClassroomItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<ClassRoomModel, ClassroomItemLayoutBinding>(ClassroomItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: ClassroomItemLayoutBinding,
                item: ClassRoomModel,
                position: Int
            ) {
                itemBinding.tvTitle.text = viewModel.getTitle(item)
                itemBinding.tvStudents.text = item.students.toString()
                itemBinding.tvSection.text = item.section
                itemBinding.cdMainContainer.setOnClickListener {}
            }
        }
    }

    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
        initRecycler()
    }

    private fun initObservers() {
        viewModel.classRoomDataList.observe(this) {
            adapter.setItems(it)
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

    private fun initBtnListener() {}

    private fun initRecycler() {
        binding.optionRecyclerViewCRF.adapter = adapter
    }


}