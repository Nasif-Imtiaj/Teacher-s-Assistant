package com.ni.ui.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ni.data.models.Student
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.MainActivityLayoutBinding
import com.ni.ui.screens.home.HomeFragment
import com.ni.ui.screens.home.HomeListener
import com.ni.ui.screens.splash.SplashFragment
import com.ni.ui.screens.splash.SplashListener
import com.ni.ui.screens.user.login.LoginFragment
import com.ni.ui.screens.user.login.LoginListener


var activityVmUserType = 1
var activityVmStudent = Student()
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
                return
            }
            lastFragmentTag = tag
            handleDoubleClick()
            val mFragmentManager: FragmentManager = supportFragmentManager
            if (clearBackStack) {
                if (mFragmentManager.isStateSaved) {
                    return
                }
                mFragmentManager.popBackStackImmediate(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
            val fragmentTransaction =
                mFragmentManager.beginTransaction()
            if (addToBackStack) fragmentTransaction.addToBackStack(tag)
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
        }
    }

    private fun handleDoubleClick() {
        Handler().postDelayed({ lastFragmentTag = "" }, 500)
    }

}