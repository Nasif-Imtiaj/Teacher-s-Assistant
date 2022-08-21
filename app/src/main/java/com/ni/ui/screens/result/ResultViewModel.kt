package com.ni.ui.screens.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ni.data.models.Marks
import com.ni.ui.activity.avmMarksData

class ResultViewModel : ViewModel() {
    val _marksList = MutableLiveData<ArrayList<Marks>>()
    val marksList: LiveData<ArrayList<Marks>>
        get() = _marksList
    var assignmentId = ""
    var assignmentName = ""
    var batch = ""

    fun generateMarksList(){
        val list = ArrayList<Marks>()
        for(i in avmMarksData){
            if(i.assignmentId==assignmentId)
                list.add(i)
        }
        _marksList.postValue(list)
    }
}