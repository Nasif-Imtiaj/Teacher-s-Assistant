package com.ni.ui.screens.user.register

import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.ni.data.models.User
import com.ni.teachersassistant.databinding.RegisterFragmentBinding
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.baseClasses.BaseObservableFragment

class RegisterFragment :
    BaseObservableFragment<RegisterFragmentBinding, RegisterListener>(RegisterFragmentBinding::inflate) {

    companion object {
        const val TAG = "RegisterFragment"
        fun newInstance() = RegisterFragment().apply {}
    }

    val viewModel by viewModels<RegisterViewModel>() {
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

    private fun initBtnListener() {
        binding.ivRegister.setOnClickListener {
            var name = binding.etName.text.toString()
            var email = binding.etEmail.text.toString()
            var password = binding.etPassword.text.toString()
            var confirm = binding.etConfirmPassword.text.toString()
            var teacher = binding.rbTeacher.isChecked
            var student = binding.rbStudent.isChecked
            var phoneNumber = binding.etPhoneNumber.text.toString()
            var type = "";
            if (teacher) type = "Teacher"
            else type = "Student"
            if (phoneNumber.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(),
                    "All the fields needs are required to be filled",
                    Toast.LENGTH_LONG).show()
            } else if (!teacher && !student) {
                Toast.makeText(requireContext(), "User type is required", Toast.LENGTH_LONG).show()
            } else if (password != confirm) {
                Toast.makeText(requireContext(), "Passwords don't match", Toast.LENGTH_LONG).show()
            } else if (password.length < 6) {
                Toast.makeText(requireContext(), "Passwords too short", Toast.LENGTH_LONG).show()
            } else {
                binding.llProgressBar.visibility = View.VISIBLE
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            binding.llProgressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), "User Created", Toast.LENGTH_SHORT)
                                .show()
                            viewModel.createUser(User(
                                FirebaseAuth.getInstance().currentUser!!.uid,
                                name,
                                type,
                                "",
                                email,
                                "",
                                phoneNumber
                            ))
                            popFragment()
                        } else {
                            binding.llProgressBar.visibility = View.GONE
                            Toast.makeText(requireContext(),
                                "User Created Failed",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.tvBack.setOnClickListener {
            popFragment()
        }
    }

    private fun initObservers() {}

    private fun initBackPressed() {
        (requireActivity()).onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popFragment()
                }
            })
    }

    override fun onStart() {
        super.onStart()
        registerListener()
    }

    private fun registerListener() {
        parentFragment?.let {
            if (it is RegisterListener) {
                registerObserver(it)
            }
        }
        if (context is RegisterListener) {
            registerObserver(context as RegisterListener)
        }
    }

    override fun onStop() {
        super.onStop()
        unRegisterListener()
    }

    private fun unRegisterListener() {
        parentFragment?.let {
            if (it is RegisterListener) {
                unRegisterObserver(it)
            }
        }
        if (context is RegisterListener) {
            unRegisterObserver(context as RegisterListener)
        }
    }
}