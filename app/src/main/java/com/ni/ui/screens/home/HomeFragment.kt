package com.ni.ui.screens.home

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ni.data.models.Student
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.HomeScreenFragmentBinding
import com.ni.ui.activity.avmUser
import com.ni.ui.activity.avmUserType
import com.ni.ui.activity.userIsStudent
import com.ni.ui.activity.userIsTeacher
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.dialogs.newStudentDialog.NewStudentDialog
import com.ni.ui.common.dialogs.newStudentDialog.NewStudentDialogListener
import com.ni.ui.screens.classList.ClassListFragment
import com.ni.ui.screens.library.LibraryFragment
import com.ni.ui.screens.studentProfile.StudentProfileFragment
import com.ni.ui.screens.teacherProfile.TeacherProfileFragment
import kotlinx.android.synthetic.main.home_screen_fragment.*
import java.io.File
import java.io.FileOutputStream

class HomeFragment :
    BaseObservableFragment<HomeScreenFragmentBinding, HomeListener>(HomeScreenFragmentBinding::inflate),
    NewStudentDialogListener {
    companion object {
        const val TAG = "HomeScreenFragment"
        fun newInstance() = HomeFragment().apply {}
    }

    val viewModel by viewModels<HomeViewModel>() {
        ViewModelFactory()
    }


    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
        initPdf()
    }

    private fun initUiListener() {
        initBtnListener()
    }

    private fun initObservers() {
        viewModel.isLoading.observe(this) {
            if (viewModel.isLoading.value == true) {
                binding.llProgressBar.visibility = View.VISIBLE
            } else {
                binding.llProgressBar.visibility = View.GONE
            }
        }
        viewModel.user.observe(this) {
            if (viewModel.getUserTpe() == "Teacher")
                avmUserType = userIsTeacher
            else
                avmUserType = userIsStudent
            if (avmUserType == userIsStudent)
                viewModel.retrieveStudent()
            initSetupUi()
        }
        viewModel.showNewStudentDialog.observe(this) {
            if (viewModel.showNewStudentDialog.value == true) {
                val dialog = NewStudentDialog.newInstance()
                dialog.show(childFragmentManager, NewStudentDialog.TAG)
            }
        }
    }

    fun initSetupUi() {
        if (avmUserType == userIsTeacher) {
            binding.tvWelcomeMsgHeader.text = "Welcome Teacher"
            binding.ivLibrary.visibility = View.VISIBLE
        } else if (avmUserType == userIsStudent) {
            binding.tvWelcomeMsgHeader.text = "Welcome Student"
            binding.ivLibrary.visibility = View.GONE
        }
        Glide.with(binding.ivAvatar)
            .load(Uri.parse(avmUser.imgUrl))
            .into(binding.ivAvatar)
        initPdf()
    }

    private fun initBackPressed() {
        (requireActivity()).onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popFragment()
                }
            })
    }

    private fun initBtnListener() {
        binding.ivAvatar.setOnClickListener {
            if (avmUserType == userIsTeacher)
                loadTeacherProfileScreen()
            else
                loadStudentProfileScreen()
        }
        binding.ivClassroom.setOnClickListener {
            loadClassListScreen()
        }
        binding.ivLibrary.setOnClickListener {
            loadLibraryScreen()
        }
        binding.ivLogout.setOnClickListener {
            logout()
        }
    }

    private fun loadTeacherProfileScreen() {
        loadSubFragment(
            TeacherProfileFragment.newInstance(),
            flHomeContainer.id,
            TeacherProfileFragment.TAG
        )
    }

    private fun loadStudentProfileScreen() {
        loadSubFragment(
            StudentProfileFragment.newInstance(),
            flHomeContainer.id,
            StudentProfileFragment.TAG
        )
    }

    private fun loadClassListScreen() {
        loadSubFragment(ClassListFragment.newInstance(), flHomeContainer.id, ClassListFragment.TAG)
    }

    private fun loadLibraryScreen() {
        loadSubFragment(LibraryFragment.newInstance(), flHomeContainer.id, LibraryFragment.TAG)
    }

    private fun logout() {
        viewModel.logout()
        notify { it.showLoginScreen() }
    }


    override fun onDialogPositiveClick(student: Student) {
        viewModel.createStudent(student)
    }

    override fun onDialogNegativeClick() {

    }

    override fun onStart() {
        super.onStart()
        registerListener()
    }

    private fun registerListener() {
        parentFragment?.let {
            if (it is HomeListener) {
                registerObserver(it)
            }
        }
        if (context is HomeListener) {
            registerObserver(context as HomeListener)
        }
    }

    override fun onStop() {
        super.onStop()
        unRegisterListener()
    }

    private fun unRegisterListener() {
        parentFragment?.let {
            if (it is HomeListener) {
                unRegisterObserver(it)
            }
        }
        if (context is HomeListener) {
            unRegisterObserver(context as HomeListener)
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

    fun initPdf(){

        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireContext().display?.getRealMetrics(displayMetrics)
            displayMetrics.densityDpi
        }
        else{
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        view?.measure(
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
            )
        )
        view?.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(requireView().measuredWidth, requireView().measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view?.draw(canvas)
        var pageW = requireView().measuredWidth
        var pageH = requireView().measuredHeight
        Bitmap.createScaledBitmap(bitmap, pageW, pageH, true)
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(pageW, pageH, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        page.canvas.drawBitmap(bitmap, 0F, 0F, null)
        pdfDocument.finishPage(page)
        val filePath = File(requireContext().getExternalFilesDir(null), "bitmapPdf.pdf")

        pdfDocument.writeTo(FileOutputStream(filePath))
        Toast.makeText(requireContext(),"${filePath.absolutePath}",Toast.LENGTH_SHORT).show()
        Log.d(TAG, "initPdf: ${filePath.absolutePath} ")
        pdfDocument.close()
    }
}