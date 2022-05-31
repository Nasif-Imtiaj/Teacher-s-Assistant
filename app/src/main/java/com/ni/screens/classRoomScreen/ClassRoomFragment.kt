package com.ni.screens.classRoomScreen

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.core.adapter.AbstractAdapter
import com.ni.core.baseClasses.BaseObservableFragment
import com.ni.core.dialogs.createClassRoomDialog.CreateClassRoomDialog
import com.ni.core.dialogs.createClassRoomDialog.CreateClassRoomDialogListener
import com.ni.models.ClassRoomModel
import com.ni.teachersassistant.databinding.ClassRoomFragmentBinding
import com.ni.teachersassistant.databinding.ClassroomItemLayoutBinding

class ClassRoomFragment :
    BaseObservableFragment<ClassRoomFragmentBinding, ClassRoomListener>(ClassRoomFragmentBinding::inflate),
    CreateClassRoomDialogListener        {
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

    private fun initBtnListener() {
        binding.fabAdd.setOnClickListener{
            val dialog = CreateClassRoomDialog()
            dialog.show(childFragmentManager, "CreateClassRoomDialog")
        }
    }

    private fun initRecycler() {
        binding.optionRecyclerViewCRF.adapter = adapter
    }

    override fun onDialogPositiveClick(dept: String, sub: String, code: String) {
        Log.d(TAG, "onDialogPositiveClick: $dept $sub $code")
    }

    override fun onDialogNegativeClick() {
       
    }

}