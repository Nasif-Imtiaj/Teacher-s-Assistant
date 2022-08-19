package com.ni.ui.screens.assignment.submit

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.ni.teachersassistant.databinding.SubmitFragmentBinding
import com.ni.ui.common.baseClasses.BaseObservableFragment


class SubmitFragment :
    BaseObservableFragment<SubmitFragmentBinding, SubmitListener>(SubmitFragmentBinding::inflate) {

    companion object {
        const val TAG = "SubmitFragment"
        const val MY_RESULT_CODE_FILECHOOSER = 101
        fun newInstance() = SubmitFragment()
    }


    val viewModel by viewModels<SubmitViewModel>()
    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
        initRecycler()
    }

    private fun initObservers() {}

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
                    MY_RESULT_CODE_FILECHOOSER
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
        startActivityForResult(chooseFileIntent, MY_RESULT_CODE_FILECHOOSER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            MY_RESULT_CODE_FILECHOOSER -> if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val fileUri = data.data
                    val name = queryFileName(requireContext().contentResolver, fileUri!!)
                    Log.d(TAG, "onActivityResult: $fileUri  $name")
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
    }
}