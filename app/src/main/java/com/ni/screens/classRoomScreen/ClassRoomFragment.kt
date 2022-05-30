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

    private var data = arrayListOf<ClassRoomModel>()

    val viewModel by viewModels<ClassRoomViewModel>()


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
            data.clear()
            data.addAll(it)
            binding.optionRecyclerViewCRF.adapter?.notifyDataSetChanged()
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

    private fun initBtnListener() {

    }

    private fun initRecycler() {
        binding.optionRecyclerViewCRF.adapter = GenericRecyclerAdapter(
            data, R.layout.classroom_item_layout,
            object : GenericSimpleRecyclerBindingInterface<ClassRoomModel> {
                override fun bindData(item: ClassRoomModel, view: View) {
                    val itemBinding = ClassroomItemLayoutBinding.inflate(layoutInflater)
                    itemBinding.tvDept.text = item.dept.toString()
                    itemBinding.tvBatch.text = item.batch.toString()
                    itemBinding.tvSection.text = item.section
                    itemBinding.tvStudents.text = item.students.toString()
                    itemBinding.tvSubject.text = item.subject
                    itemBinding.tvCode.text = item.subjectCode
                }
            })
    }


}