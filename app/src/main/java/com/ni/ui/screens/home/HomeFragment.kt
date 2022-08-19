package com.ni.ui.screens.home

import android.net.Uri
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ni.data.models.Student
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.HomeScreenFragmentBinding
import com.ni.ui.activity.avmUser
import com.ni.ui.activity.avmUserType
import com.ni.ui.activity.userIsStudent
import com.ni.ui.activity.userIsTeacher
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.dialogs.newStudentDialog.NewStudentDialog
import com.ni.ui.common.dialogs.newStudentDialog.NewStudentDialogListener
import com.ni.ui.screens.classList.ClassListFragment
import com.ni.ui.screens.library.LibraryFragment
import com.ni.ui.screens.studentProfile.StudentProfileFragment
import com.ni.ui.screens.teacherProfile.TeacherProfileFragment
import kotlinx.android.synthetic.main.home_screen_fragment.*

class HomeFragment :
    BaseObservableFragment<HomeScreenFragmentBinding, HomeListener>(HomeScreenFragmentBinding::inflate),
    NewStudentDialogListener {
    companion object {
        const val TAG = "HomeScreenFragment"
        fun newInstance() = HomeFragment().apply {}
    }

    val viewModel by viewModels<HomeViewModel>() {
        ViewModelFactory()
    }


    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
    }

    private fun initObservers() {
        viewModel.isLoading.observe(this) {
            if (viewModel.isLoading.value == true) {
                binding.llProgressBar.visibility = View.VISIBLE
            } else {
                binding.llProgressBar.visibility = View.GONE
            }
        }
        viewModel.user.observe(this) {
            if (viewModel.getUserTpe() == "Teacher")
                avmUserType = userIsTeacher
            else
                avmUserType = userIsStudent
            if (avmUserType == userIsStudent)
                viewModel.retrieveStudent()
            initSetupUi()
        }
        viewModel.showNewStudentDialog.observe(this) {
            if (viewModel.showNewStudentDialog.value == true) {
                val dialog = NewStudentDialog.newInstance()
                dialog.show(childFragmentManager, NewStudentDialog.TAG)
            }
        }
    }

    fun initSetupUi() {
        if (avmUserType == userIsTeacher) {
            binding.tvWelcomeMsgHeader.text = "Welcome Teacher"
            binding.ivLibrary.visibility = View.VISIBLE
        } else if (avmUserType == userIsStudent) {
            binding.tvWelcomeMsgHeader.text = "Welcome Student"
            binding.ivLibrary.visibility = View.GONE
        }
        Glide.with(binding.ivAvatar)
            .load(Uri.parse(avmUser.imgUrl))
            .into(binding.ivAvatar)
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
        binding.ivAvatar.setOnClickListener {
            if (avmUserType == userIsTeacher)
                loadTeacherProfileScreen()
            else
                loadStudentProfileScreen()
        }
        binding.ivClassroom.setOnClickListener {
            loadClassListScreen()
        }
        binding.ivLibrary.setOnClickListener {
            loadLibraryScreen()
        }
        binding.ivLogout.setOnClickListener {
            logout()
        }
    }

    private fun loadTeacherProfileScreen() {
        loadSubFragment(
            TeacherProfileFragment.newInstance(),
            flHomeContainer.id,
            TeacherProfileFragment.TAG
        )
    }

    private fun loadStudentProfileScreen() {
        loadSubFragment(
            StudentProfileFragment.newInstance(),
            flHomeContainer.id,
            StudentProfileFragment.TAG
        )
    }

    private fun loadClassListScreen() {
        loadSubFragment(ClassListFragment.newInstance(), flHomeContainer.id, ClassListFragment.TAG)
    }

    private fun loadLibraryScreen() {
        loadSubFragment(LibraryFragment.newInstance(), flHomeContainer.id, LibraryFragment.TAG)
    }

    private fun logout() {
        viewModel.logout()
        notify { it.showLoginScreen() }
    }


    override fun onDialogPositiveClick(student: Student) {
        viewModel.createStudent(student)
    }

    override fun onDialogNegativeClick() {

    }

    override fun onStart() {
        super.onStart()
        registerListener()
    }

    private fun registerListener() {
        parentFragment?.let {
            if (it is HomeListener) {
                registerObserver(it)
            }
        }
        if (context is HomeListener) {
            registerObserver(context as HomeListener)
        }
    }

    override fun onStop() {
        super.onStop()
        unRegisterListener()
    }

    private fun unRegisterListener() {
        parentFragment?.let {
            if (it is HomeListener) {
                unRegisterObserver(it)
            }
        }
        if (context is HomeListener) {
            unRegisterObserver(context as HomeListener)
        }
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