package com.ni.screens.classRoomScreen


import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ni.core.adapter.AbstractAdapter
import com.ni.core.baseClasses.BaseObservableFragment
import com.ni.models.AssignmentModel
import com.ni.models.StudentInfoModel
import com.ni.screens.assignmentScreen.AssignmentScreenFragment
import com.ni.screens.studentProfileScreen.StudentProfileFragment
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.AssignmentItemLayoutBinding
import com.ni.teachersassistant.databinding.ClassRoomFragmentBinding
import com.ni.teachersassistant.databinding.StudentItemLayoutBinding

class ClassRoomFragment :
    BaseObservableFragment<ClassRoomFragmentBinding, ClassRoomListener>(ClassRoomFragmentBinding::inflate) {
    companion object {
        const val TAG = "ClassRoomFragment"
        fun newInstance(roomId: String) = ClassRoomFragment().apply {
            this.roomId = roomId
        }
    }

    var roomId = ""
    val viewModel by viewModels<ClassRoomViewModel>()

    private val studentAdapter: AbstractAdapter<StudentInfoModel, StudentItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<StudentInfoModel, StudentItemLayoutBinding>(StudentItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: StudentItemLayoutBinding,
                item: StudentInfoModel,
                position: Int
            ) {
                Log.d(TAG, "bind: ")
                itemBinding.tvId.text = item.studentId
                itemBinding.tvDept.text = item.dept
                itemBinding.tvBatch.text = item.batch
                itemBinding.tvSection.text = item.section
                itemBinding.cvMainContainer.setOnClickListener {
                    loadStudentProfile()
                }
            }
        }
    }

    private val assignmentAdapter: AbstractAdapter<AssignmentModel, AssignmentItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<AssignmentModel, AssignmentItemLayoutBinding>(
                AssignmentItemLayoutBinding::inflate
            ) {
            override fun bind(
                itemBinding: AssignmentItemLayoutBinding,
                item: AssignmentModel,
                position: Int
            ) {
                itemBinding.tvTitle.text = item.name
                itemBinding.tvDueDate.text = item.endDate
                itemBinding.tvCounter.text = "16/20"
                itemBinding.acMainContainer.setOnClickListener {
                    loadAssignmentScreen(item.name)
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
        viewModel.filterByClassRoom(roomId)
    }

    private fun initObservers() {
        viewModel.studentDataList.observe(this) {
            studentAdapter.setItems(it)
        }
        viewModel.assignmentDataList.observe(this) {
            assignmentAdapter.setItems(it)
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
        binding.tvBtnStudents.setOnClickListener {
            binding.optionRecyclerViewCRF.adapter = studentAdapter
        }
        binding.tvBtnClassWork.setOnClickListener {
            binding.optionRecyclerViewCRF.adapter = assignmentAdapter
        }
    }

    private fun initRecycler() {
        binding.optionRecyclerViewCRF.adapter = studentAdapter
    }

    fun loadStudentProfile() {
        var fragment = StudentProfileFragment.newInstance()
        loadSubFragment(fragment, R.id.flCRFContainer, StudentProfileFragment.TAG)
    }

    fun loadAssignmentScreen(assignmentName:String) {
        var fragment = AssignmentScreenFragment.newInstance(assignmentName)
        loadSubFragment(fragment, R.id.flCRFContainer, AssignmentScreenFragment.TAG)
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