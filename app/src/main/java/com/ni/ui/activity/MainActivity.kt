package com.ni.ui.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.MainActivityLayoutBinding
import com.ni.ui.screens.home.HomeFragment
import com.ni.ui.screens.home.HomeListener
import com.ni.ui.screens.splash.SplashFragment
import com.ni.ui.screens.splash.SplashListener
import com.ni.ui.screens.user.login.LoginFragment
import com.ni.ui.screens.user.login.LoginListener



class MainActivity : AppCompatActivity(), HomeListener, LoginListener, SplashListener {

    private lateinit var binding: MainActivityLayoutBinding
    private var lastFragmentTag: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        loadFragment(SplashFragment.newInstance(), true, false, SplashFragment.TAG)
    }

    private fun loadLoginScreen() {
        loadFragment(LoginFragment.newInstance(), true, false, LoginFragment.TAG)
    }

    private fun loadHomeScreen() {
        loadFragment(HomeFragment.newInstance(), true, false, HomeFragment.TAG)
    }

    override fun onSuccessfulLogin() {
        loadHomeScreen()
    }

    override fun showHomeScreen() {
        loadHomeScreen()
    }

    override fun showLoginScreen() {
        loadLoginScreen()
    }

    fun loadFragment(
        newFragment: Fragment,
        addToBackStack: Boolean = true,
        clearBackStack: Boolean = false,
        tag: String,
        enterTransition: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
        container: Int = R.id.fragmentContainer,
    ) {
        try {
            if (lastFragmentTag == tag) {
                //Toaster.debugToast(this, "Eto speed e click kora jabe na")
                return
            }
            lastFragmentTag = tag
            handleDoubleClick()
            val mFragmentManager: FragmentManager = supportFragmentManager
            if (clearBackStack) {
                if (mFragmentManager.isStateSaved) {
                    // If the state is saved we can't clear the back stack. Simply not doing this, but
                    // still replacing fragment is a bad idea. Therefore we abort the entire operation.
                    return
                }
                // Remove all entries from back stack
                mFragmentManager.popBackStackImmediate(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
            val fragmentTransaction =
                mFragmentManager.beginTransaction()
            if (addToBackStack) fragmentTransaction.addToBackStack(tag)
            // Change to a new fragment
            fragmentTransaction.add(
                container,
                newFragment,
                tag
            )
            fragmentTransaction.setTransition(enterTransition)
            if (mFragmentManager.isStateSaved) {
                fragmentTransaction.commitAllowingStateLoss()
            } else {
                fragmentTransaction.commit()
            }
        } catch (ex: Exception) {
            //    Toaster.debugToast(this, "Fragment transaction failed 70 ${ex.message}")
        }
    }

    private fun handleDoubleClick() {
        Handler().postDelayed({ lastFragmentTag = "" }, 500)
    }

}