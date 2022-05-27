package com.ni.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ni.screens.homeScreen.HomeScreenFragment
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.MainActivityLayoutBinding

class MainActivity  : AppCompatActivity() {
    private lateinit var binding: MainActivityLayoutBinding
    private var lastFragmentTag: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        loadFragment(HomeScreenFragment.newInstance(),true,false,HomeScreenFragment.TAG)
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