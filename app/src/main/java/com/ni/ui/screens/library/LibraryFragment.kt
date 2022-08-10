package com.ni.ui.screens.library
import androidx.activity.OnBackPressedCallback
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
    private val bookletListAdapter: AbstractAdapter<Booklet, BookletItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Booklet, BookletItemLayoutBinding>(BookletItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: BookletItemLayoutBinding,
                item: Booklet,
                position: Int
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
    }

    private fun initBtnListener() {
        binding.bUpload.setOnClickListener {}
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