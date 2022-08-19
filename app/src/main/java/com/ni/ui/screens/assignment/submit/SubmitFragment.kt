package com.ni.ui.screens.assignment.submit

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.ni.teachersassistant.databinding.SubmitFragmentBinding
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.baseClasses.BaseObservableFragment

class SubmitFragment :
    BaseObservableFragment<SubmitFragmentBinding, SubmitListener>(SubmitFragmentBinding::inflate) {

    companion object {
        const val TAG = "SubmitFragment"
        const val ASSIGNMENTID = "AssignmentId"
        const val ASSIGNMENTNAME = "AssignmentName"
        const val MY_RESULT_CODE_FILE_CHOOSER = 101
        fun newInstance(assignmentId: String, assignmentName: String) = SubmitFragment().apply {
            this.arguments = Bundle().apply {
                putString(ASSIGNMENTID, assignmentId)
                putString(ASSIGNMENTNAME, assignmentName)
            }
        }
    }

    val viewModel by viewModels<SubmitViewModel>() {
        ViewModelFactory()
    }

    override fun initView() {
        initUiListener()
        initObservers()
        initGetArguments()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
        initRecycler()
    }

    private fun initGetArguments() {
        viewModel.assignmentId = arguments?.getString(ASSIGNMENTID).toString()
        viewModel.assignmentName = arguments?.getString(ASSIGNMENTNAME).toString()
    }

    private fun initObservers() {
        viewModel.isLoading.observe(this) {
            if (viewModel.isLoading.value == true) {
                binding.llProgressBar.visibility = View.VISIBLE
            } else {
                binding.llProgressBar.visibility = View.GONE
            }
        }
        viewModel.showToastMsg.observe(this) {
            Toast.makeText(requireContext(), viewModel.showToastMsg.value, Toast.LENGTH_SHORT)
                .show()
        }
        viewModel.fileName.observe(this) {
            binding.tvFileName.text = viewModel.fileName.value
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

    private fun initRecycler() {}

    private fun askPermissionAndBrowseFile() {
        // With Android Level >= 23, you have to ask the user
        // for permission to access External Storage.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Level 23

            // Check if we have Call permission
            val permission = ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_RESULT_CODE_FILE_CHOOSER
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
        startActivityForResult(chooseFileIntent, MY_RESULT_CODE_FILE_CHOOSER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            MY_RESULT_CODE_FILE_CHOOSER -> if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val fileUri = data.data
                    val name = queryFileName(requireContext().contentResolver, fileUri!!)
                    viewModel.localFileUri = fileUri.toString()
                    viewModel.setFileName(name!!)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun queryFileName(resolver: ContentResolver, uri: Uri): String? {
        val returnCursor = resolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    private fun initBtnListener() {
        binding.bUpload.setOnClickListener {
            askPermissionAndBrowseFile()
        }
        binding.bSubmit.setOnClickListener {
            viewModel.startSubmitProcess()
        }
    }
}