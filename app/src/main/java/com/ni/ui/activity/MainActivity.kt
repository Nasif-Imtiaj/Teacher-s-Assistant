package com.ni.ui.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.MainActivityLayoutBinding
import com.ni.ui.screens.classList.ClassListFragment
import com.ni.ui.screens.home.HomeFragment
import com.ni.ui.screens.home.HomeListener
import com.ni.ui.screens.library.LibraryFragment
import com.ni.ui.screens.teacherProfile.TeacherProfileFragment
import com.ni.ui.screens.user.login.LoginFragment
import com.ni.ui.screens.user.login.LoginListener
import com.ni.ui.screens.user.register.RegisterFragment
import com.ni.ui.screens.user.register.RegisterListener
import io.realm.BuildConfig
import io.realm.Realm
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.User
import kotlinx.android.synthetic.main.main_activity_layout.*

lateinit var taskApp: App
inline fun <reified T> T.TAG(): String = T::class.java.simpleName
const val PARTITION_EXTRA_KEY = "PARTITION"
const val PROJECT_NAME_EXTRA_KEY = "PROJECT NAME"


class MainActivity : AppCompatActivity(), HomeListener, LoginListener, RegisterListener {
    private var user: User? = null
    private lateinit var binding: MainActivityLayoutBinding
    private var lastFragmentTag: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRealmDatabase()
        init()
    }

    private fun init() {
        user = taskApp.currentUser()
        if (user == null) {
            loadLoginScreen()
        } else {
            loadHomeScreen()
        }
    }

    private fun loadLoginScreen() {
        loadFragment(LoginFragment.newInstance(), true, false, LoginFragment.TAG)
    }

    private fun loadRegisterScreen() {
        loadFragment(RegisterFragment.newInstance(), true, false, RegisterFragment.TAG)
    }

    private fun loadHomeScreen() {
        loadFragment(HomeFragment.newInstance(), true, false, HomeFragment.TAG)
    }

    private fun loadTeacherProfile() {
        loadFragment(TeacherProfileFragment.newInstance(), true, false, TeacherProfileFragment.TAG)
    }

    private fun loadClassList() {
        loadFragment(ClassListFragment.newInstance(), true, false, ClassListFragment.TAG)
    }

    private fun loadLibrary() {
        loadFragment(LibraryFragment.newInstance(), true, false, ClassListFragment.TAG)

    }

    override fun onTeacherProfileClicked() {
        loadTeacherProfile()
    }

    override fun onClassRoomClicked() {
        loadClassList()
    }

    override fun onLibraryClicked() {
        loadLibrary()
    }

    override fun onLogoutClicked() {
        user?.logOutAsync {
            if (it.isSuccess) {
                user = null
                this.supportFragmentManager.popBackStack()
                loadLoginScreen()
            } else {
                Toast.makeText(this,"Logout failed!!",Toast.LENGTH_LONG)
            }
        }
    }

    override fun onSuccessfulLogin() {
        user = taskApp.currentUser()
        loadHomeScreen()
    }

    override fun onRegisterClicked() {
        loadRegisterScreen()
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

    fun initRealmDatabase() {
        Realm.init(this)
        taskApp = App(
            AppConfiguration.Builder("teacher_assistant-xxpjn")
                .defaultSyncErrorHandler { session, error ->
                    Log.e(TAG(), "Sync error: ${error.errorMessage}")
                }
                .build())

        // Enable more logging in debug mode
        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.ALL)
        }

        Log.v(TAG(), "Initialized the Realm App configuration for: ${taskApp.configuration.appId}")
    }

    override fun onRegisteredSuccessfully() {
       loadHomeScreen()
    }
}