package com.ni.ui.screens.assignment.submit

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.Booklet
import com.ni.data.models.Submit
import com.ni.data.repository.remote.FirebaseStorageCallbacks
import com.ni.data.repository.remote.FirebaseStorageRepository
import com.ni.data.repository.remote.SubmitRepository
import com.ni.ui.activity.avmStudent
import com.ni.ui.common.baseClasses.BaseViewModel
import java.util.*
import kotlin.collections.ArrayList

class SubmitViewModel(
    private val submitRepository: SubmitRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository
) : BaseViewModel() {

    var folderName = "Assignment"
    var subFolderName = ""
    var submitFileName = ""
    var assignmentId = ""
    var assignmentName = ""
    var localFileUri = ""
    var remoteFileUri = ""
    private val _showToastMsg = MutableLiveData<String>()
    val showToastMsg: LiveData<String>
        get() = _showToastMsg
    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String>
        get() = _fileName


    private fun createSubmit(submit: Submit) {
        submitRepository.create(submit)
    }

    private fun uploadToStorage(
        folderName: String,
        subFolderName: String,
        fileName: String,
        localUri: String
    ) {
        firebaseStorageRepository.create(
            folderName,
            subFolderName,
            fileName,
            localUri,
            object : FirebaseStorageCallbacks {
                override fun onSuccess(url: String) {
                    remoteFileUri = url
                    _isLoading.postValue(false)
                    submitFile()
                    _showToastMsg.postValue("Successfully submitted")
                }

                override fun onSuccessBooklets(list: ArrayList<Booklet>) {
                    TODO("Not yet implemented")
                }

                override fun onFailed() {
                    _isLoading.postValue(false)
                    _showToastMsg.postValue("Failed to submit, please try again")
                }
            }
        )
    }

    fun submitFile() {
        createSubmit(
            Submit(
                avmStudent.id + assignmentId,
                avmStudent.id,
                avmStudent.studentId,
                assignmentId,
                remoteFileUri,
                false,
                0.0f,
                0.0f,
                0.0f,
                0.0f
            )
        )
    }

    fun startSubmitProcess() {
        _isLoading.postValue(true)
        subFolderName = avmStudent.studentId
        submitFileName = assignmentName
        uploadToStorage(folderName, subFolderName, submitFileName, localFileUri)
    }

    fun setFileName(name:String){
        _fileName.postValue("File Name : $name")
    }
}