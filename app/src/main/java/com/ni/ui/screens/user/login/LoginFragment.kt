package com.ni.ui.screens.user.login

import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.ni.teachersassistant.databinding.LoginFragmentBinding
import com.ni.ui.activity.fAuth
import com.ni.ui.common.baseClasses.BaseObservableFragment


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
        binding.ivLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            validUser(email, password)
        }
        binding.tvRegister.setOnClickListener {
            popFragment()
            notify {
                it.onRegisterClicked()
            }
        }
    }

    private fun validUser(email: String, password: String) {
        binding.llProgressBar.visibility = View.VISIBLE
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            binding.llProgressBar.visibility = View.GONE
            if (it.isSuccessful) {
                popFragment()
                notify {
                    it.onSuccessfulLogin()
                }
            } else {
                Toast.makeText(requireContext(), "Incorrect email/password", Toast.LENGTH_SHORT)
                    .show()
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