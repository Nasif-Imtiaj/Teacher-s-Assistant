package com.ni.ui.screens.user.login

import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.ni.teachersassistant.databinding.LoginFragmentBinding
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.ui.screens.user.register.RegisterFragment
import kotlinx.android.synthetic.main.login_fragment.*


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
            loadRegisterScreen()
        }
    }

    private fun loadRegisterScreen(){
        loadSubFragment(RegisterFragment.newInstance(),flLoginContainer.id,RegisterFragment.TAG )
    }

    private fun validUser(email: String, password: String) {
        binding.llProgressBar.visibility = View.VISIBLE
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
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