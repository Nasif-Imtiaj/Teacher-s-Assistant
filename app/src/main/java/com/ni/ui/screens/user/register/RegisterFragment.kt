package com.ni.ui.screens.user.register

import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.ni.teachersassistant.databinding.RegisterFragmentBinding
import com.ni.ui.activity.taskApp
import com.ni.ui.common.baseClasses.BaseObservableFragment
class RegisterFragment :BaseObservableFragment<RegisterFragmentBinding,RegisterListener>(RegisterFragmentBinding::inflate) {

    companion object {
        const val TAG = "RegisterFragment"
        fun newInstance() = RegisterFragment().apply {}
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
        binding.bRegister.setOnClickListener{
            var email = binding.etEmail.text.toString()
            var password = binding.etPassword.text.toString()
            var confirm = binding.etConfirmPassword.text.toString()
            if(password!=confirm){
                Log.d(TAG, "initBtnListener: failed $email $password $confirm")
                Toast.makeText(requireContext(),"Passwords don't match",Toast.LENGTH_LONG)
            }else{
                taskApp.emailPassword.registerUserAsync(email, password) {
                    if (!it.isSuccess) {
                        Log.d(TAG, "initBtnListener: failed $email $password")
                        Toast.makeText(requireContext(),"Could not register user.",Toast.LENGTH_LONG)
                    } else {
                        Log.d(TAG, "initBtnListener: $email $password")
                        popFragment()
                        notify { it.onRegisteredSuccessfully() }
                    }
                }
            }
        }
    }

    private fun initObservers() {

    }
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