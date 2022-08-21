package com.ni.ui.screens.assignment.submission

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.ni.data.models.Submit
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.SubmissionFragmentBinding
import com.ni.teachersassistant.databinding.SubmitItemLayoutBinding
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.adapter.AbstractAdapter
import com.ni.ui.common.baseClasses.BaseObservableFragment
import kotlinx.android.synthetic.main.submit_item_layout.*


class SubmissionFragment :
    BaseObservableFragment<SubmissionFragmentBinding, SubmissionListener>(SubmissionFragmentBinding::inflate) {

    companion object {
        const val TAG = "SubmissionFragment"
        const val ASSIGNMENTID = "AssignmentId"
        fun newInstance(assignmentId: String) = SubmissionFragment().apply {
            this.arguments = Bundle().apply {
                putString(ASSIGNMENTID, assignmentId)
            }
        }
    }

    val viewModel by viewModels<SubmissionViewModel>() {
        ViewModelFactory()
    }

    private val submissionListAdapter: AbstractAdapter<Submit, SubmitItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Submit, SubmitItemLayoutBinding>(SubmitItemLayoutBinding::inflate) {
            @SuppressLint("ClickableViewAccessibility")
            override fun bind(
                itemBinding: SubmitItemLayoutBinding,
                item: Submit,
                position: Int,
            ) {
                itemBinding.tvId.text = item.studentId
                itemBinding.etObtained.setText(item.obtained.toString())
                itemBinding.etBonus.setText(item.bonus.toString())
                itemBinding.etPenalty.setText(item.penalty.toString())
                itemBinding.tvTotal.text = item.total.toString()
                itemBinding.tvAnswerScript.setOnClickListener {
                    openFile(item.answerScriptUrl)
                }
                if (item.checked) {
                    itemBinding.rbYes.isChecked = true
                    itemBinding.rbNo.isChecked = false
                } else {
                    itemBinding.rbYes.isChecked = false
                    itemBinding.rbNo.isChecked = true
                }
                itemBinding.etObtained.addTextChangedListener {
                    itemBinding.ivUpdate.visibility = View.VISIBLE
                }
                itemBinding.etBonus.addTextChangedListener {
                    itemBinding.ivUpdate.visibility = View.VISIBLE
                }
                itemBinding.etPenalty.addTextChangedListener {
                    itemBinding.ivUpdate.visibility = View.VISIBLE
                }
                itemBinding.ivUpdate.setOnClickListener {
                    calculate(itemBinding)
                    itemBinding.ivUpdate.visibility = View.GONE
                }
            }
        }
    }

    fun toggleTvUpdate(value: Boolean) {
        if (value) {
            binding.tvUpdate.isClickable = true
            binding.tvUpdate.setBackgroundResource(R.drawable.text_rounded_selected_green)
        } else {
            binding.tvUpdate.isClickable = false
            binding.tvUpdate.setBackgroundResource(R.drawable.text_rounded_unselected)
        }
    }

    private fun ifValidNumber(number: Float): Float {
        if (number < 0.0) return 0f
        if (number > 100.0) return 100f
        return number
    }


    fun calculate(itemBinding: SubmitItemLayoutBinding) {
        var obtained = ifValidNumber(etObtained.text.toString().toFloat())
        var bonus = ifValidNumber(etBonus.text.toString().toFloat())
        var penalty = ifValidNumber(etPenalty.text.toString().toFloat())
        itemBinding.etObtained.setText(obtained.toString())
        itemBinding.etBonus.setText(bonus.toString())
        itemBinding.etPenalty.setText(penalty.toString())
        var tot = ifValidNumber(obtained + bonus - penalty)
        itemBinding.tvTotal.text = tot.toString()
        viewModel.enableUpdate(true)
    }

    fun openFile(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
        startActivity(intent)
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

    private fun initRecycler() {
        binding.optionRecyclerViewSLF.adapter = submissionListAdapter
    }


    private fun initObservers() {
        viewModel.isLoading.observe(this) {
            if (viewModel.isLoading.value == true)
                binding.llProgressBar.visibility = View.VISIBLE
            else
                binding.llProgressBar.visibility = View.GONE
        }
        viewModel.enableUpdate.observe(this){
            toggleTvUpdate(it)
        }
        viewModel.assignmentId.observe(this) {
            viewModel.retrieveSubmission()
        }
        viewModel.subissionList.observe(this) {
            submissionListAdapter.setItems(it)
            notify { it.showGenerateResult() }
            viewModel.addToMarksData()
        }
        viewModel.enableUpdate.observe(this) {
            if (it == true) {
                binding.tvUpdate.isClickable = true
                binding.tvUpdate.setBackgroundResource(R.drawable.text_rounded_selected_green)
            } else {
                binding.tvUpdate.isClickable = false
                binding.tvUpdate.setBackgroundResource(R.drawable.text_rounded_unselected)
            }
        }
    }

    private fun initGetArguments() {
        viewModel.setAssignmentId(arguments?.getString(ASSIGNMENTID).toString())

    }

    private fun initBackPressed() {
        (requireActivity()).onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popFragment()
                }
            })
    }


    private fun initBtnListener() {}

    override fun onStart() {
        super.onStart()
        registerListener()
    }

    private fun registerListener() {
        parentFragment?.let {
            if (it is SubmissionListener) {
                registerObserver(it)
            }
        }
        if (context is SubmissionListener) {
            registerObserver(context as SubmissionListener)
        }
    }

    override fun onStop() {
        super.onStop()
        unRegisterListener()
    }

    private fun unRegisterListener() {
        parentFragment?.let {
            if (it is SubmissionListener) {
                unRegisterObserver(it)
            }
        }
        if (context is SubmissionListener) {
            unRegisterObserver(context as SubmissionListener)
        }
    }

}