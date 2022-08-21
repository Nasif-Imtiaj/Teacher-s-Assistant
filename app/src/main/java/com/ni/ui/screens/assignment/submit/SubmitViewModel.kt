package com.ni.ui.screens.assignment.submit

import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
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
import com.ni.utils.FileUtils
import com.thoughtleaf.textsumarizex.DocumentReaderUtil
import java.io.File
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
    var localFilePath = ""
    var remoteFileUri = ""
    private val _showToastMsg = MutableLiveData<String>()
    val showToastMsg: LiveData<String>
        get() = _showToastMsg
    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String>
        get() = _fileName
    private val _startTextExtraction = MutableLiveData<Boolean>()
    val startTextExtraction: LiveData<Boolean>
        get() = _startTextExtraction
    private val _answerText = MutableLiveData<String>()
    val answerText: LiveData<String>
        get() = _answerText


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

                    _showToastMsg.postValue("Successfully submitted")
                    saveACopy()
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
                answerText.value!!,
                false,
                0.0f,
                0.0f,
                0.0f,
                0.0f
            )
        )
        _isLoading.postValue(false)
    }

    fun startSubmitProcess() {
        _isLoading.postValue(true)
        subFolderName = avmStudent.studentId
        submitFileName = assignmentName + ".pdf"
        uploadToStorage(folderName, subFolderName, submitFileName, localFileUri)
    }

    fun setFileName(name: String) {
        _fileName.postValue("File Name : $name")
    }

    fun saveACopy() {
        _isLoading.postValue(true)
        firebaseStorageRepository.retrieve(
            folderName,
            subFolderName,
            submitFileName,
            object : FirebaseStorageCallbacks {
                override fun onSuccess(url: String) {
                    _startTextExtraction.postValue(true)
                }

                override fun onFailed() {
                    _isLoading.postValue(false)
                }

            }
        )
    }

    fun postAnswerText(s: String) {
        _answerText.postValue(s)
    }
}