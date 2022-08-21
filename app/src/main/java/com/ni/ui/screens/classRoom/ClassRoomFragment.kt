package com.ni.ui.screens.classRoom

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.ni.data.models.Assignment
import com.ni.data.models.Student
import com.ni.ui.common.adapter.AbstractAdapter
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.ui.screens.assignment.AssignmentFragment
import com.ni.ui.screens.studentProfile.StudentProfileFragment
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.AssignmentItemLayoutBinding
import com.ni.teachersassistant.databinding.ClassRoomFragmentBinding
import com.ni.teachersassistant.databinding.StudentItemLayoutBinding
import com.ni.ui.activity.avmUserType
import com.ni.ui.activity.userIsStudent
import com.ni.ui.activity.userIsTeacher
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.dialogs.newAssignmentDialog.NewAssignmentDialog
import com.ni.ui.common.dialogs.newAssignmentDialog.NewAssignmentDialogListener

class ClassRoomFragment :
    BaseObservableFragment<ClassRoomFragmentBinding, ClassRoomListener>(ClassRoomFragmentBinding::inflate),
    NewAssignmentDialogListener {
    companion object {
        const val TAG = "ClassRoomFragment"
        const val CLASSROOMID = "classroomId"
        const val CREATORID = "creatorId"
        const val CLASSROOMCOURSENAME = "classroomCourseName"
        const val BATCH = "batch"
        fun newInstance(classroomId: String, creatorId: String,courseName:String,batch:String) = ClassRoomFragment().apply {
            this.arguments = Bundle().apply {
                putString(CLASSROOMID, classroomId)
                putString(CREATORID, creatorId)
                putString(CLASSROOMCOURSENAME,courseName)
                putString(BATCH,batch)
            }
        }
    }

    val viewModel by viewModels<ClassRoomViewModel>() {
        ViewModelFactory()
    }

    private val studentAdapter: AbstractAdapter<Student, StudentItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Student, StudentItemLayoutBinding>(StudentItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: StudentItemLayoutBinding,
                item: Student,
                position: Int,
            ) {
                itemBinding.tvId.text = item.studentId
                itemBinding.tvDept.text = item.department
                itemBinding.tvBatch.text = item.batch
                itemBinding.tvSection.text = item.section
                itemBinding.cvMainContainer.setOnClickListener {
                    loadStudentProfile()
                }
            }
        }
    }

    private val assignmentAdapter: AbstractAdapter<Assignment, AssignmentItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Assignment, AssignmentItemLayoutBinding>(
                AssignmentItemLayoutBinding::inflate
            ) {
            override fun bind(
                itemBinding: AssignmentItemLayoutBinding,
                item: Assignment,
                position: Int,
            ) {
                itemBinding.tvName.text = item.name
                itemBinding.tvStartDate.text = item.startDate
                itemBinding.tvEndDate.text = item.endDate
                itemBinding.tvExamMarks.text = "Marks: " + item.mark.toString()
                itemBinding.acMainContainer.setOnClickListener {
                    loadAssignmentScreen(item)
                }
            }
        }
    }

    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
        initGetArguments()
        viewModel.retrieveAssignment()
        viewModel.retrieveEnrollment()
        if (avmUserType == userIsTeacher && FirebaseAuth.getInstance().currentUser?.uid == viewModel.creatorId) {
            binding.ivAddAssignment.visibility = View.VISIBLE
        }
        binding.tvTitle.text =viewModel.courseName
    }

    private fun initGetArguments(){
        viewModel.classroomId = arguments?.getString(CLASSROOMID).toString()
        viewModel.creatorId = arguments?.getString(CREATORID).toString()
        viewModel.courseName = arguments?.getString(CLASSROOMCOURSENAME).toString()
        viewModel.batch = arguments?.getString(BATCH).toString()
    }

    private fun initUiListener() {
        initRecycler()
        initBtnListener()
    }

    private fun initObservers() {
        viewModel.studentDataList.observe(this) {
            studentAdapter.setItems(it)
        }
        viewModel.assignmentDataList.observe(this) {
            assignmentAdapter.setItems(it)
        }
        viewModel.enrollmentDataList.observe(this) {
            viewModel.generateEnrolledStudentList()
        }
        viewModel.enrolledStudentList.observe(this) {
            viewModel.retrieveStudent()
        }
        viewModel.showEnrollOption.observe(this) {
            if (avmUserType == userIsStudent) {
                if (viewModel.showEnrollOption.value == true)
                    binding.ivEnroll.visibility = View.VISIBLE
                else
                    binding.ivEnroll.visibility = View.GONE
            }
        }
        viewModel.isLoading.observe(this) {
            if (viewModel.isLoading.value == true) {
                binding.llProgressBar.visibility = View.VISIBLE
            } else {
                binding.llProgressBar.visibility = View.GONE
            }
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
        binding.tvStudents.setOnClickListener {
            binding.optionRecyclerViewCRF.adapter = studentAdapter
            binding.tvStudents.setBackgroundResource(R.drawable.text_rounded_selected)
            binding.tvClassWork.setBackgroundResource(R.drawable.text_rounded_unselected)
        }
        binding.tvClassWork.setOnClickListener {
            binding.optionRecyclerViewCRF.adapter = assignmentAdapter
            binding.tvStudents.setBackgroundResource(R.drawable.text_rounded_unselected)
            binding.tvClassWork.setBackgroundResource(R.drawable.text_rounded_selected)
        }
        binding.ivEnroll.setOnClickListener {
            viewModel.enrollNewStudent()
        }
        binding.ivAddAssignment.setOnClickListener {
            val dialog = NewAssignmentDialog.newInstance(viewModel.classroomId)
            dialog.show(childFragmentManager, NewAssignmentDialog.TAG)
        }
    }

    private fun initRecycler() {
        binding.optionRecyclerViewCRF.adapter = studentAdapter
    }

    fun loadStudentProfile() {
        var fragment = StudentProfileFragment.newInstance()
        loadSubFragment(fragment, R.id.flCRFContainer, StudentProfileFragment.TAG)
    }

    fun loadAssignmentScreen(assignment: Assignment) {
        var fragment = AssignmentFragment.newInstance(assignment)
        loadSubFragment(fragment, R.id.flCRFContainer, AssignmentFragment.TAG)
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
        }
    }


    override fun onDialogPositiveClick(assignment: Assignment) {
        assignment.batch = viewModel.batch
        viewModel.createAssignment(assignment)
    }

    override fun onDialogNegativeClick() {

    }
}