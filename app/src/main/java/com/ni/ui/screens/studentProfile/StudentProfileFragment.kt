package com.ni.ui.screens.studentProfile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.StudentProfileFragmentBinding
import com.ni.ui.activity.avmStudent
import com.ni.ui.activity.avmUser
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.screens.assignment.submit.SubmitFragment
import kotlinx.android.synthetic.main.login_fragment.*

class StudentProfileFragment :
    BaseObservableFragment<StudentProfileFragmentBinding, StudentProfileListener>(
        StudentProfileFragmentBinding::inflate
    ) {

    companion object {
        var TAG = "StudentProfileFragment"
        fun newInstance() = StudentProfileFragment()
    }

    val viewModel by viewModels<StudentProfileViewModel>() {
        ViewModelFactory()
    }


    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
        initSetupUi()
    }

    private fun initUiListener() {
        initBtnListener()

    }

    private fun initSetupUi() {
        Glide.with(binding.ivProfileImg)
            .load(Uri.parse(avmUser.imgUrl))
            .into(binding.ivProfileImg)
        binding.tvDisplayName.text = avmStudent.name
        binding.tvDisplayId.text = avmStudent.studentId
        binding.tvDisplayDepartment.text = avmStudent.department
        binding.tvDisplayBatch.text = avmStudent.batch
        binding.tvDisplaySection.text = avmStudent.section
        binding.tvDisplayEmail.text = avmUser.email
        binding.tvDisplayPhone.text = avmUser.phoneNumber
        Glide.with(binding.ivCurrentIcon)
            .load(Uri.parse(avmUser.imgUrl))
            .into(binding.ivCurrentIcon)
        viewModel.updateImgUrl(avmUser.imgUrl)

        binding.etdisplayName.setText(avmStudent.name)
        binding.etDisplayId.setText(avmStudent.studentId)
        binding.etDisplayDepartment.setText(avmStudent.department)
        binding.etDisplayBatch.setText(avmStudent.batch)
        binding.etDisplaySection.setText(avmStudent.section)
        binding.etDisplayEmail.setText(avmUser.email)
        binding.etDisplayPhone.setText(avmUser.phoneNumber)
    }


    private fun initObservers() {
        viewModel.imgUrl.observe(this) {
            Glide.with(binding.ivCurrentIcon)
                .load(Uri.parse(viewModel.imgUrl.value))
                .into(binding.ivCurrentIcon)
        }
        viewModel.remoteUrl.observe(this) {
            avmUser.name = binding.etdisplayName.text.toString()
            avmUser.imgUrl = viewModel.remoteUrl.value.toString()
            avmUser.email = binding.etDisplayEmail.text.toString()
            avmUser.phoneNumber = binding.etDisplayEmail.text.toString()
            viewModel.updateUser()
            avmStudent.name = binding.etdisplayName.text.toString()
            avmStudent.studentId = binding.etDisplayId.text.toString()
            avmStudent.department = binding.etDisplayDepartment.text.toString()
            avmStudent.batch = binding.etDisplayBatch.text.toString()
            avmStudent.section = binding.etDisplaySection.text.toString()
            viewModel.updateStudent()
             Glide.with(binding.ivProfileImg)
                .load(Uri.parse(avmUser.imgUrl))
                .into(binding.ivProfileImg)
            binding.acUpdateStudentInfo.visibility = View.GONE
        }
        viewModel.isLoading.observe(this){
            if(viewModel.isLoading.value==true)
                llProgressBar.visibility = View.VISIBLE
            else
                llProgressBar.visibility = View.GONE
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

    private fun initBtnListener() {
        binding.ivBackBtn.setOnClickListener {
            popFragment()
        }
        binding.ivSettingsBtn.setOnClickListener {
            binding.acUpdateStudentInfo.visibility = View.VISIBLE
        }
        binding.bCancel.setOnClickListener {
            binding.acUpdateStudentInfo.visibility = View.GONE
        }
        binding.bSave.setOnClickListener {
            viewModel.updateImgInDatabase()
        }
        binding.tvUpdateImgIcon.setOnClickListener {
            askPermissionAndBrowseFile()
        }
    }

    private fun askPermissionAndBrowseFile() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Check if we have Call permission
            val permission = ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    SubmitFragment.MY_RESULT_CODE_FILE_CHOOSER
                )
                return
            }
        }
        this.doBrowseFile()
    }

    private fun doBrowseFile() {
        var chooseFileIntent = Intent(Intent.ACTION_GET_CONTENT)
        chooseFileIntent.type = "*/*"
        // Only return URIs that can be opened with ContentResolver
        chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE)
        chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose a file")
        startActivityForResult(chooseFileIntent, SubmitFragment.MY_RESULT_CODE_FILE_CHOOSER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SubmitFragment.MY_RESULT_CODE_FILE_CHOOSER -> if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val fileUri = data.data
                    viewModel.updateImgUrl(fileUri.toString())
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}