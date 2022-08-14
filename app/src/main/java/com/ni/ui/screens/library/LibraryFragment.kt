package com.ni.ui.screens.library
import android.R
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
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

    val viewModel by viewModels<LibraryViewModel>{ LibraryViewModelFactory() }

    private val bookletListAdapter: AbstractAdapter<Booklet, BookletItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Booklet, BookletItemLayoutBinding>(BookletItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: BookletItemLayoutBinding,
                item: Booklet,
                position: Int,
            ) {
                itemBinding.tvName.text = item.name
                Glide.with(itemBinding.ivPic).load(Uri.parse(item.remoteThumbUrl)).into(itemBinding.ivPic)
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
    }

    private fun initBtnListener() {
        binding.bUpload.setOnClickListener {
            Log.d(TAG, "initBtnListener: UploadFile")
             uploadFile()
            Toast.makeText(activity,"UploadClicked",Toast.LENGTH_SHORT)
        }
    }

    private fun uploadFile(){
        val url = Uri.parse("android.resource://" + activity?.packageName.toString() + "/" + R.drawable.ic_dialog_alert)
        val booklet = Booklet("1","Test",url.toString(),"","","")
        viewModel.uploadFile(booklet)
    }

    private fun downloadFile(){

    }

    private fun initRecycler() {
        binding.optionRecyclerViewLF.adapter = bookletListAdapter
    }

    private fun initObservers() {
        viewModel.libraryBookletList.observe(this){
            bookletListAdapter.setItems(it)
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
}