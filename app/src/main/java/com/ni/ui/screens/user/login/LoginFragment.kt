package com.ni.ui.screens.user.login

import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.ni.teachersassistant.databinding.LoginFragmentBinding
import com.ni.ui.activity.taskApp
import com.ni.ui.common.baseClasses.BaseObservableFragment
import io.realm.mongodb.Credentials


class LoginFragment :
    BaseObservableFragment<LoginFragmentBinding, LoginListener>(LoginFragmentBinding::inflate) {
    companion object {
        const val TAG = "LoginFragment"
        fun newInstance() = LoginFragment().apply {}
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

    }

    private fun initBtnListener() {
            binding.bLogin.setOnClickListener{
                var email = binding.etEmail.text.toString()
                var password = binding.etPassword.text.toString()
                validUser(email,password)
            }
        binding.bRegister.setOnClickListener {
             popFragment()
            notify {
                it.onRegisterClicked()
            }
        }
    }

    private fun validUser(email:String,password:String){
        val creds = Credentials.emailPassword(email, password)
        Log.d(TAG, "validUser: $email $password")
        taskApp.loginAsync(creds) {
            Log.d(TAG, "validUser1: $email $password")
            if (it.isSuccess) {
                Log.d(TAG, "validUser2: $email $password")
                popFragment()
                notify {
                   it.onSuccessfulLogin()
                }
            }
            else {
                Log.d(TAG, "validUser3: $email $password")
                Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT)
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
    override fun onStart() {
        super.onStart()
        registerListener()
    }

    private fun registerListener() {
        parentFragment?.let {
            if (it is LoginListener) {
                registerObserver(it)
            }
        }
        if (context is LoginListener) {
            registerObserver(context as LoginListener)
        }
    }

    override fun onStop() {
        super.onStop()
        unRegisterListener()
    }

    private fun unRegisterListener() {
        parentFragment?.let {
            if (it is LoginListener) {
                unRegisterObserver(it)
            }
        }
        if (context is LoginListener) {
            unRegisterObserver(context as LoginListener)
        }
    }
}