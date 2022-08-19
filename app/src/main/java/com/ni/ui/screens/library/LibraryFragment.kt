package com.ni.ui.screens.library
import android.R
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ni.data.models.Booklet
import com.ni.teachersassistant.databinding.BookletItemLayoutBinding
import com.ni.teachersassistant.databinding.LibraryFragmentLayoutBinding
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.adapter.AbstractAdapter
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.utils.FileUtils
import java.io.File

class LibraryFragment : BaseObservableFragment<LibraryFragmentLayoutBinding, LibraryListener>(
    LibraryFragmentLayoutBinding::inflate
) {
    companion object {
        const val TAG = "LibraryFragment"
        fun newInstance() = LibraryFragment().apply {}
    }

    val viewModel by viewModels<LibraryViewModel>{ ViewModelFactory() }

    private val bookletListAdapter: AbstractAdapter<Booklet, BookletItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Booklet, BookletItemLayoutBinding>(BookletItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: BookletItemLayoutBinding,
                item: Booklet,
                position: Int,
            ) {
                itemBinding.tvName.text = item.name
                if(fileExists(item)){
                    itemBinding.ivShare.visibility= View.VISIBLE
                    itemBinding.ivDownload.visibility = View.GONE
                }
                itemBinding.ivDownload.setOnClickListener {
                    downloadFile(item)
                }
                itemBinding.tvName.setOnClickListener {
                    openFile(item)
                }
                itemBinding.ivShare.setOnClickListener {
                    shareFile(item)
                }
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
        binding.ivUpload.setOnClickListener {
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

    private fun fileExists(booklet: Booklet):Boolean{
        return FileUtils.isFileExistsOnDownload(booklet.name+".png")
    }

    private fun downloadFile(booklet: Booklet){
        viewModel.downloadFile(booklet)
    }

    private fun openFile(booklet: Booklet){
            var file = File(FileUtils.getRootDownloadDirectory() ,  booklet.name+".png")
            val uriPdfPath = FileProvider.getUriForFile(requireContext(), requireActivity().packageName + ".provider", file)
            val pdfOpenIntent =  Intent(Intent.ACTION_VIEW)
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uriPdfPath.toString()))
            pdfOpenIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            pdfOpenIntent.clipData = ClipData.newRawUri("", uriPdfPath)
            pdfOpenIntent.setDataAndType(uriPdfPath, mimeType)
            pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pdfOpenIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            try {
                startActivity(pdfOpenIntent);
            } catch (e:Exception) {
                Toast.makeText(requireContext(),"There is no app to load corresponding PDF",Toast.LENGTH_LONG).show()
            }
        }

    private fun shareFile(booklet: Booklet){
        var file = File(FileUtils.getRootDownloadDirectory() ,  booklet.name+".png")
        val uriPdfPath = FileProvider.getUriForFile(requireContext(), requireActivity().packageName + ".provider", file)
        val pdfOpenIntent =  Intent(Intent.ACTION_SEND)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uriPdfPath.toString()))
        pdfOpenIntent.setType(mimeType)
        pdfOpenIntent.putExtra(Intent.EXTRA_STREAM,uriPdfPath)
        try {
            startActivity(pdfOpenIntent);
        } catch (e:Exception) {
            Toast.makeText(requireContext(),"There is no app to load corresponding PDF",Toast.LENGTH_LONG).show()
        }
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