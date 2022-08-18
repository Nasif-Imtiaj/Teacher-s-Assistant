package com.ni.ui.screens.splash


import android.os.Handler
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.ni.teachersassistant.databinding.FragmentSplashBinding
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.baseClasses.BaseObservableFragment


class SplashFragment :
    BaseObservableFragment<FragmentSplashBinding, SplashListener>(FragmentSplashBinding::inflate) {

    companion object {
        const val TAG = "SplashFragment"
        fun newInstance() = SplashFragment().apply {}
    }

    val viewModel by viewModels<SplashViewModel> { ViewModelFactory() }

    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
        Handler(Looper.getMainLooper()).postDelayed({
            decideNextScreen()
        }, 1000)

    }

    private fun decideNextScreen() {
        popFragment()
        if (FirebaseAuth.getInstance().currentUser == null) {
            notify { it.showLoginScreen() }
        } else {
            notify { it.showHomeScreen() }
        }
    }

    private fun initUiListener() {}

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
            if (it is SplashListener) {
                registerObserver(it)
            }
        }
        if (context is SplashListener) {
            registerObserver(context as SplashListener)
        }
    }

    override fun onStop() {
        super.onStop()
        unRegisterListener()
    }

    private fun unRegisterListener() {
        parentFragment?.let {
            if (it is SplashListener) {
                unRegisterObserver(it)
            }
        }
        if (context is SplashListener) {
            unRegisterObserver(context as SplashListener)
        }
    }
}