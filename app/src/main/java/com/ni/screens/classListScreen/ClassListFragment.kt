package com.ni.screens.classListScreen


import androidx.fragment.app.Fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.core.adapter.AbstractAdapter
import com.ni.core.baseClasses.BaseObservableFragment
import com.ni.dialogs.createClassRoomDialog.CreateClassRoomDialog
import com.ni.dialogs.createClassRoomDialog.CreateClassRoomDialogListener
import com.ni.models.ClassListModel
import com.ni.screens.classRoomScreen.ClassRoomFragment
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.ClassListFragmentBinding
import com.ni.teachersassistant.databinding.ClassroomItemLayoutBinding

class ClassListFragment :
    BaseObservableFragment<ClassListFragmentBinding, ClassListListener>(ClassListFragmentBinding::inflate),
    CreateClassRoomDialogListener {

    companion object {
        const val TAG = "ClassListFragment"
        fun newInstance() = ClassListFragment().apply {}
    }

    val viewModel by viewModels<ClassListViewModel>()

    private val classListAdapter: AbstractAdapter<ClassListModel, ClassroomItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<ClassListModel, ClassroomItemLayoutBinding>(ClassroomItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: ClassroomItemLayoutBinding,
                item: ClassListModel,
                position: Int
            ) {
                itemBinding.tvTitle.text = viewModel.getTitle(item)
                itemBinding.tvStudents.text = item.students.toString()
                itemBinding.tvSection.text = item.section
                itemBinding.cdMainContainer.setOnClickListener {
                    loadClassRoom(item.classRoomId)
                }
            }
        }
    }

    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
    }

    private fun initUiListener() {
        initRecycler()
        initBtnListener()
    }

    private fun initObservers() {
        viewModel.classRoomDataList.observe(this) {
            classListAdapter.setItems(it)
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
        binding.fabAdd.setOnClickListener {
            val dialog = CreateClassRoomDialog()
            dialog.show(childFragmentManager, CreateClassRoomDialog.TAG)
        }
    }

    private fun initRecycler() {
        binding.optionRecyclerViewCLF.adapter = classListAdapter
    }

    override fun onDialogPositiveClick(dept: String, sub: String, code: String) {
        viewModel.addToClassRoom(dept, sub, code)
    }

    override fun onDialogNegativeClick() {

    }

    private fun loadClassRoom(roomId: String) {
        var fragment = ClassRoomFragment.newInstance(roomId)
        loadSubFragment(fragment, R.id.flFraContainer, ClassRoomFragment.TAG)
    }

    private fun loadSubFragment(
        newFragment: Fragment,
        containerId: Int,
        fragmentTag: String,
    ) {
        try {
            childFragmentManager.beginTransaction()
                .replace(containerId, newFragment, fragmentTag)
                .addToBackStack(fragmentTag)

                .commitAllowingStateLoss()
        } catch (ex: Exception) {
            //Toaster.debugToast(this, "Fragment transaction failed 70 ${ex.message}")
        }
    }

}