package com.ni.ui.screens.library

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.ni.data.models.Booklet
import com.ni.teachersassistant.databinding.BookletItemLayoutBinding
import com.ni.teachersassistant.databinding.LibraryFragmentLayoutBinding
import com.ni.ui.common.ViewModelFactory
import com.ni.ui.common.adapter.AbstractAdapter
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.ui.screens.assignment.submit.SubmitFragment
import com.ni.utils.FileUtils
import java.io.File

class LibraryFragment : BaseObservableFragment<LibraryFragmentLayoutBinding, LibraryListener>(
    LibraryFragmentLayoutBinding::inflate
) {
    companion object {
        const val TAG = "LibraryFragment"
        fun newInstance() = LibraryFragment().apply {}
    }

    val viewModel by viewModels<LibraryViewModel> { ViewModelFactory() }

    private val bookletListAdapter: AbstractAdapter<Booklet, BookletItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Booklet, BookletItemLayoutBinding>(BookletItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: BookletItemLayoutBinding,
                item: Booklet,
                position: Int,
            ) {
                itemBinding.tvName.text = item.name
                if (fileExists(item.name)) {
                    itemBinding.ivShare.visibility = View.VISIBLE
                    itemBinding.ivDownload.visibility = View.GONE
                }
                itemBinding.ivDownload.setOnClickListener {
                    downloadFile(item)
                }
                itemBinding.tvName.setOnClickListener {
                    openFile(item.name)
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
            viewModel.uploadToStorage()
        }
        binding.llUpload.setOnClickListener {
            askPermissionAndBrowseFile()
        }
    }

    private fun fileExists(fileName: String): Boolean {
        return FileUtils.isFileExistsOnDownload(fileName)
    }

    private fun downloadFile(booklet: Booklet) {
        viewModel.downloadFile(booklet)
    }

    private fun openFile(fileName: String) {
        var file = File(viewModel.getDownloadedFile(), fileName)
        val uri = FileProvider.getUriForFile(
            requireContext(),
            requireActivity().packageName + ".provider",
            file
        )
        val pdfOpenIntent = Intent(Intent.ACTION_VIEW)
        val mimeType = MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uri.toString()))
        pdfOpenIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        pdfOpenIntent.clipData = ClipData.newRawUri("", uri)
        pdfOpenIntent.setDataAndType(uri, mimeType)
        pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        pdfOpenIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        try {
            startActivity(pdfOpenIntent);
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "There is no app to load corresponding PDF",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun shareFile(booklet: Booklet) {
        var file = File(FileUtils.getRootDownloadDirectory(), booklet.name)
        val uriPdfPath = FileProvider.getUriForFile(
            requireContext(),
            requireActivity().packageName + ".provider",
            file
        )
        val pdfOpenIntent = Intent(Intent.ACTION_SEND)
        val mimeType = MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uriPdfPath.toString()))
        pdfOpenIntent.setType(mimeType)
        pdfOpenIntent.putExtra(Intent.EXTRA_STREAM, uriPdfPath)
        try {
            startActivity(pdfOpenIntent);
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "There is no app to load corresponding PDF",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun initRecycler() {
        binding.optionRecyclerViewLF.adapter = bookletListAdapter
    }

    private fun initObservers() {
        viewModel.isLoading.observe(this) {
            if (viewModel.isLoading.value == true) {
                binding.llProgressBar.visibility = View.VISIBLE
            } else {
                binding.llProgressBar.visibility = View.GONE
            }
        }
        viewModel.showEmptyListMsg.observe(this) {
            if (viewModel.showEmptyListMsg.value == true) {
                binding.tvEmpty.visibility = View.VISIBLE
            }
        }
        viewModel.toastMsg.observe(this) {
            Toast.makeText(requireContext(), viewModel.toastMsg.value, Toast.LENGTH_SHORT).show()
        }
        viewModel.libraryBookletList.observe(this) {
            bookletListAdapter.setItems(it)
        }
        viewModel.localFileUrl.observe(this) {
            queryFileName(
                requireContext().contentResolver,
                Uri.parse(viewModel.localFileUrl.value)
            )?.let { it1 ->
                viewModel.updateFilename(it1)
            }
        }
        viewModel.remoteFileUrl.observe(this) {
            viewModel.createBooklet()
        }
        viewModel.fileName.observe(this) {
            binding.tvFileName.text = viewModel.fileName.value
            binding.tvFileName.visibility = View.VISIBLE
            binding.ivUpload.visibility = View.VISIBLE
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

    private fun askPermissionAndBrowseFile() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Check if we have Call permission
            val permission = ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    SubmitFragment.MY_RESULT_CODE_FILE_CHOOSER
                )
                return
            }
        }
        this.doBrowseFile()
    }

    private fun doBrowseFile() {
        var chooseFileIntent = Intent(Intent.ACTION_GET_CONTENT)
        chooseFileIntent.type = "*/*"
        // Only return URIs that can be opened with ContentResolver
        chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE)
        chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose a file")
        startActivityForResult(chooseFileIntent, SubmitFragment.MY_RESULT_CODE_FILE_CHOOSER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SubmitFragment.MY_RESULT_CODE_FILE_CHOOSER -> if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val fileUri = data.data
                    viewModel.updateLocalFileUrl(fileUri.toString())
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun queryFileName(resolver: ContentResolver, uri: Uri): String? {
        val returnCursor = resolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }
}