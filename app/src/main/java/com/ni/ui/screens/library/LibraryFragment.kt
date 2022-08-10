package com.ni.ui.screens.library

import android.R
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ni.data.models.Booklet
import com.ni.teachersassistant.databinding.BookletItemLayoutBinding
import com.ni.teachersassistant.databinding.LibraryFragmentLayoutBinding
import com.ni.ui.common.adapter.AbstractAdapter
import com.ni.ui.common.baseClasses.BaseObservableFragment

class LibraryFragment : BaseObservableFragment<LibraryFragmentLayoutBinding, LibraryListener>(
    LibraryFragmentLayoutBinding::inflate
) {
    companion object {
        const val TAG = "LibraryFragment"
        fun newInstance() = LibraryFragment().apply {}
    }
    lateinit var storage: FirebaseStorage
    private val bookletListAdapter: AbstractAdapter<Booklet, BookletItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Booklet, BookletItemLayoutBinding>(BookletItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: BookletItemLayoutBinding,
                item: Booklet,
                position: Int,
            ) {
                itemBinding.tvName.text = item.name
            }
        }
    }

    override fun initView() {
        initUiListener()
        initObservers()
        initBackPressed()
    }

    private fun initUiListener() {
        initBtnListener()
        initRecycler()
        storage = Firebase.storage
    }

    private fun initBtnListener() {
        binding.bUpload.setOnClickListener {
            Log.d(TAG, "initBtnListener: UploadFile")
             uploadFile()
            Toast.makeText(activity,"UploadClicked",Toast.LENGTH_SHORT)
        }
    }

    private fun uploadFile(){
        var fileName = "testName"
        var storageRef = storage.reference.child("library/").child(fileName)
        val url = Uri.parse("android.resource://" + activity?.packageName.toString() + "/" + R.drawable.ic_delete)
        var uploadTask = storageRef.putFile(url)

        uploadTask.addOnFailureListener {
         Toast.makeText(activity,"Failed",Toast.LENGTH_SHORT)
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    private fun initRecycler() {
        binding.optionRecyclerViewLF.adapter = bookletListAdapter
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
}