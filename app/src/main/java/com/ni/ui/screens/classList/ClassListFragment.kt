package com.ni.ui.screens.classList


import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.ni.data.models.Classroom
import com.ni.ui.common.adapter.AbstractAdapter
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.ui.common.dialogs.createClassRoomDialog.CreateClassRoomDialog
import com.ni.ui.common.dialogs.createClassRoomDialog.CreateClassRoomDialogListener
import com.ni.ui.screens.classRoom.ClassRoomFragment
import com.ni.teachersassistant.R
import com.ni.teachersassistant.databinding.ClassListFragmentBinding
import com.ni.teachersassistant.databinding.ClassroomItemLayoutBinding
import com.ni.ui.activity.avmUserType
import com.ni.ui.activity.userIsTeacher
import com.ni.ui.common.ViewModelFactory

class ClassListFragment :
    BaseObservableFragment<ClassListFragmentBinding, ClassListListener>(ClassListFragmentBinding::inflate),
    CreateClassRoomDialogListener {

    companion object {
        const val TAG = "ClassListFragment"
        fun newInstance() = ClassListFragment().apply {}
    }

    val viewModel by viewModels<ClassListViewModel> {
        ViewModelFactory()
    }

    private val classListAdapter: AbstractAdapter<Classroom, ClassroomItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Classroom, ClassroomItemLayoutBinding>(ClassroomItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: ClassroomItemLayoutBinding,
                item: Classroom,
                position: Int,
            ) {
                itemBinding.tvTitle.text = viewModel.getTitle(item)
                itemBinding.tvSection.text = item.section + " Section"
                itemBinding.cdMainContainer.setOnClickListener {
                    loadClassRoom(item.id,item.creatorId)
                }
                itemBinding.acMainContainer.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor(viewModel.getColor()))
            }
        }
    }

    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
        if(avmUserType== userIsTeacher){
            binding.ivAddClassroom.visibility = View.VISIBLE
        }else{
            binding.ivAddClassroom.visibility = View.GONE
        }
    }

    private fun initUiListener() {
        initRecycler()
        initBtnListener()
    }

    private fun initObservers() {
        viewModel.classRoomDataList.observe(this) {
            classListAdapter.setItems(it)
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
        binding.ivAddClassroom.setOnClickListener {
            val dialog = CreateClassRoomDialog()
            dialog.show(childFragmentManager, CreateClassRoomDialog.TAG)
        }
    }

    private fun initRecycler() {
        binding.optionRecyclerViewCLF.adapter = classListAdapter
    }

    override fun onDialogPositiveClick(classroom: Classroom) {
        viewModel.createClassroom(classroom)
    }

    override fun onDialogNegativeClick() {

    }

    private fun loadClassRoom(roomId: String,creatorId: String) {
        var fragment = ClassRoomFragment.newInstance(roomId,creatorId)
        loadSubFragment(fragment, R.id.flFraContainer, ClassRoomFragment.TAG)
    }

    private fun loadSubFragment(
        newFragment: Fragment,
        containerId: Int,
        fragmentTag: String,
    ) {
        try {
            childFragmentManager.beginTransaction()
                .replace(containerId, newFragment, fragmentTag)
                .addToBackStack(fragmentTag)

                .commitAllowingStateLoss()
        } catch (ex: Exception) {
            //Toaster.debugToast(this, "Fragment transaction failed 70 ${ex.message}")
        }
    }

}