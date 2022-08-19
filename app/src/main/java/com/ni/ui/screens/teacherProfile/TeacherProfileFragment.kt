package com.ni.ui.screens.teacherProfile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ni.teachersassistant.databinding.TeacherProfileFragmentBinding
import com.ni.ui.activity.avmUser
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.ui.screens.assignment.submit.SubmitFragment


class TeacherProfileFragment :
    BaseObservableFragment<TeacherProfileFragmentBinding, TeacherProfileListener>(
        TeacherProfileFragmentBinding::inflate
    ) {

    companion object {
        const val TAG = "TeacherProfileFragment"
        fun newInstance() = TeacherProfileFragment().apply {}
    }

    val viewModel by viewModels<TeacherProfileViewModel>() {
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
        Glide.with(binding.ivProfilePic)
            .load(Uri.parse(avmUser.imgUrl))
            .into(binding.ivProfilePic)
        binding.tvName.text = avmUser.name
        Glide.with(binding.ivCurrentIcon)
            .load(Uri.parse(avmUser.imgUrl))
            .into(binding.ivCurrentIcon)
        viewModel.updateImgUrl(avmUser.imgUrl)
    }

    private fun initObservers() {
        viewModel.imgUrl.observe(this) {
            Glide.with(binding.ivCurrentIcon)
                .load(Uri.parse(viewModel.imgUrl.value))
                .into(binding.ivCurrentIcon)
        }
        viewModel.toastMsg.observe(this){
            if(viewModel.toastMsg.value=="Success"){
                Toast.makeText(requireContext(),"Successfully updated.",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"Failed to update!!",Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.remoteUrl.observe(this){
            avmUser.imgUrl = viewModel.remoteUrl.value.toString()
            avmUser.email = binding.etUpdateEmail.text.toString()
            avmUser.phoneNumber = binding.etUpdatePhoneNumber.text.toString()
            avmUser.linkdin = binding.etUpdateLindkin.text.toString()
            viewModel.updateUser()
            Glide.with(binding.ivProfilePic)
                .load(Uri.parse(avmUser.imgUrl))
                .into(binding.ivProfilePic)
            binding.acUpdateView.visibility = View.GONE
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
        binding.ivContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:${avmUser.phoneNumber}"))
            startActivity(intent)
        }
        binding.ivEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto:${avmUser.email}"))
            startActivity(intent)
        }
        binding.ivLinkdin.setOnClickListener {
            if (avmUser.linkdin.isNotEmpty()) {
                var intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(avmUser.linkdin))
                startActivity(intent)
            }
        }
        binding.ivSettingsBtn.setOnClickListener {
            showUpdate()
        }
        binding.tvUpdate.setOnClickListener {
            askPermissionAndBrowseFile()
        }
        binding.bSave.setOnClickListener {
            viewModel.updateImgInDatabase()
        //    binding.acUpdateView.visibility = View.GONE
        }
    }

    private fun showUpdate() {
        binding.acUpdateView.visibility = View.VISIBLE
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