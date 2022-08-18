package com.ni.ui.screens.home

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.HomeScreenFragmentBinding
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.screens.classList.ClassListFragment
import com.ni.ui.screens.library.LibraryFragment
import com.ni.ui.screens.teacherProfile.TeacherProfileFragment
import kotlinx.android.synthetic.main.home_screen_fragment.*

class HomeFragment :
    BaseObservableFragment<HomeScreenFragmentBinding, HomeListener>(HomeScreenFragmentBinding::inflate) {
    companion object {
        const val TAG = "HomeScreenFragment"
        fun newInstance() = HomeFragment().apply {}
    }

    val viewModel by viewModels<HomeViewModel>(){
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
        viewModel.user.observe(this){

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
        binding.ivTeacherAva.setOnClickListener {
            loadTeacherProfileScreen()
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
        loadSubFragment(TeacherProfileFragment.newInstance(),
            flHomeContainer.id,
            TeacherProfileFragment.TAG)
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