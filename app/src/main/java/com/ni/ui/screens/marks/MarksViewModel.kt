package com.ni.ui.screens.marks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.Marks
import com.ni.ui.activity.avmMarksData

class MarksViewModel : ViewModel() {
    val _marksList = MutableLiveData<ArrayList<Marks>>()
    val marksList: LiveData<ArrayList<Marks>>
        get() = _marksList
    var assignmentId = ""
    var assignmentName = ""
    var batch = ""
    var filePath = ""
    var fileName = ""

    fun generateMarksList(){
        val list = ArrayList<Marks>()
        for(i in avmMarksData){
            if(i.assignmentId==assignmentId)
                list.add(i)
        }
        Log.d("TAG", "generateMarksList: ${list}")
        Log.d("TAG", "generateMarksList: ${avmMarksData}")
        _marksList.postValue(list)
    }
}