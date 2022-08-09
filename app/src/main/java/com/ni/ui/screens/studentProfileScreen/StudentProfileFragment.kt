package com.ni.ui.screens.studentProfileScreen

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.teachersassistant.databinding.StudentProfileFragmentBinding

class StudentProfileFragment :
    BaseObservableFragment<StudentProfileFragmentBinding, StudentProfileListener>(
        StudentProfileFragmentBinding::inflate
    ) {

    companion object {
        var TAG = "StudentProfileFragment"
        fun newInstance() = StudentProfileFragment()
    }

    val viewModel by viewModels<StudentProfileViewModel>()


    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
        var markList = ArrayList<BarEntry>()
        markList.add(BarEntry(90f,0))
        markList.add(BarEntry(50f,1))
        markList.add(BarEntry(70f,2))
        markList.add(BarEntry(80f,3))
        var subList = ArrayList<String>()
        subList.add("OOP")
        subList.add("P-200")
        subList.add("CGM")
        subList.add("DS")
        var dataSet = BarDataSet(markList,"marks")
        var data = BarData(subList,dataSet)
        binding.barGraph.data = data
        binding.barGraph.setTouchEnabled(true)
        binding.barGraph.isDragEnabled = true
        binding.barGraph.setScaleEnabled(true)


    }

    private fun initObservers() {

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

    }
}