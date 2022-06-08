package com.ni.screens.classListScreen


import androidx.fragment.app.Fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.ni.core.adapter.AbstractAdapter
import com.ni.core.baseClasses.BaseObservableFragment
import com.ni.dialogs.createClassRoomDialog.CreateClassRoomDialog
import com.ni.dialogs.createClassRoomDialog.CreateClassRoomDialogListener
import com.ni.models.ClassRoomModel
import com.ni.models.StudentInfoModel
import com.ni.screens.studentProfileScreen.StudentProfileFragment
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.ClassListFragmentBinding
import com.ni.teachersassistant.databinding.ClassroomItemLayoutBinding
import com.ni.teachersassistant.databinding.StudentItemLayoutBinding

class ClassListFragment : BaseObservableFragment<ClassListFragmentBinding,ClassListListener>(ClassListFragmentBinding::inflate),
    CreateClassRoomDialogListener {

    companion object {
        const val TAG = "ClassListFragment"
        fun newInstance() = ClassListFragment().apply {}
    }

    val viewModel by viewModels<ClassListViewModel>()

    private val classRoomAdapter: AbstractAdapter<ClassRoomModel, ClassroomItemLayoutBinding> by lazy {
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
                itemBinding.cdMainContainer.setOnClickListener {
                    viewModel.filterByClassRoom(item.classRoomId)
                    binding.optionRecyclerViewCLF.adapter = studentAdapter
                }
            }
        }
    }

    private val studentAdapter: AbstractAdapter<StudentInfoModel, StudentItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<StudentInfoModel, StudentItemLayoutBinding>(StudentItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: StudentItemLayoutBinding,
                item: StudentInfoModel,
                position: Int
            ) {
                itemBinding.tvId.text = item.studentId
                itemBinding.tvDept.text = item.dept
                itemBinding.tvBatch.text = item.batch
                itemBinding.tvSection.text = item.section
                itemBinding.cvMainContainer.setOnClickListener {
                    // binding.optionRecyclerViewCRF.adapter = classRoomAdapter
                    loadStudentProfile()
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
        initBtnListener()
        initRecycler()
    }

    private fun initObservers() {
        viewModel.classRoomDataList.observe(this) {
            classRoomAdapter.setItems(it)
        }
        viewModel.studentDataList.observe(this) {
            studentAdapter.setItems(it)
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
            dialog.show(childFragmentManager, "CreateClassRoomDialog")
        }
    }

    private fun initRecycler() {
        binding.optionRecyclerViewCLF.adapter = classRoomAdapter
    }

    override fun onDialogPositiveClick(dept: String, sub: String, code: String) {
        viewModel.addToClassRoom(dept, sub, code)
    }

    override fun onDialogNegativeClick() {

    }

    fun loadStudentProfile(){
        var fragment = StudentProfileFragment.newInstance()
        loadSubFragment(fragment, R.id.flFraContainer, StudentProfileFragment.TAG)
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