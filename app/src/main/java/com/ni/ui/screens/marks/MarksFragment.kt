package com.ni.ui.screens.marks

import android.content.ClipData
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.ni.data.models.Marks
import com.ni.teachersassistant.databinding.FragmentMarksBinding
import com.ni.teachersassistant.databinding.MarksItemLayoutBinding
import com.ni.ui.common.adapter.AbstractAdapter
import com.ni.ui.common.baseClasses.BaseObservableFragment
import com.ni.ui.screens.home.HomeFragment
import com.ni.utils.FileUtils
import java.io.*


class MarksFragment :
    BaseObservableFragment<FragmentMarksBinding, MarksListener>(FragmentMarksBinding::inflate) {

    companion object {
        const val TAG = "ResultFragment"
        const val ASSIGNMENTID = "AssignmentId"
        const val ASSIGNMENTNAME = "AssignmentName"
        const val BATCH = "Batch"
        fun newInstance(assignmentId: String, assignmentName: String, batch: String) =
            MarksFragment().apply {
                this.arguments = Bundle().apply {
                    putString(ASSIGNMENTID, assignmentId)
                    putString(ASSIGNMENTNAME, assignmentName)
                    putString(BATCH, batch)
                }
            }
    }

    private val marksListAdapter: AbstractAdapter<Marks, MarksItemLayoutBinding> by lazy {
        object :
            AbstractAdapter<Marks, MarksItemLayoutBinding>(MarksItemLayoutBinding::inflate) {
            override fun bind(
                itemBinding: MarksItemLayoutBinding,
                item: Marks,
                position: Int,
            ) {
                itemBinding.tvStudentId.text = item.studentId
                itemBinding.tvMarks.text = item.obtainedMarks.toString()
            }
        }
    }

    val viewModel by viewModels<MarksViewModel>()

    override fun initView() {
        initUiListener()
        initObservers()
        initGetArguments()
        initSetupUi()
        initBackPressed()
    }


    private fun initUiListener() {
        initBtnListener()
        initRecycler()
    }

    private fun initSetupUi() {
        binding.tvAssignmentName.text = viewModel.assignmentName
        binding.tvBatch.text = "Batch : " + viewModel.batch
    }

    fun initBtnListener() {
        binding.tvPrint.setOnClickListener {
            initPdf()
        }
        binding.tvOpen.setOnClickListener {
            openFile()
        }
    }

    fun openFile(){
        var file = File(FileUtils.getRootDownloadDirectory(), viewModel.fileName)
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

    fun initRecycler() {
        binding.optionRecyclerViewRSF.adapter = marksListAdapter
    }

    fun initObservers() {
        viewModel.marksList.observe(this) {
            marksListAdapter.setItems(it)
        }
    }

    private fun initGetArguments() {
        viewModel.assignmentId = arguments?.getString(ASSIGNMENTID).toString()
        viewModel.assignmentName = arguments?.getString(ASSIGNMENTNAME).toString()
        viewModel.fileName = viewModel.assignmentName+".pdf"
        viewModel.batch = arguments?.getString(BATCH).toString()
        viewModel.generateMarksList()
    }

    private fun initBackPressed() {
        (requireActivity()).onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    popFragment()
                }
            })
    }

    fun initPdf(){
        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireContext().display?.getRealMetrics(displayMetrics)
            displayMetrics.densityDpi
        }
        else{
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        view?.measure(
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
            )
        )
        view?.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(requireView().measuredWidth, requireView().measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view?.draw(canvas)
        var pageW = requireView().measuredWidth
        var pageH = requireView().measuredHeight
        Bitmap.createScaledBitmap(bitmap, pageW, pageH, true)
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(pageW, pageH, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        page.canvas.drawBitmap(bitmap, 0F, 0F, null)
        pdfDocument.finishPage(page)
        val filePath = File(FileUtils.getRootDownloadDirectory(), viewModel.fileName)
        pdfDocument.writeTo(FileOutputStream(filePath))
        viewModel.filePath = filePath.absolutePath
        Toast.makeText(requireContext(),"${filePath.absolutePath}", Toast.LENGTH_SHORT).show()
        Log.d(HomeFragment.TAG, "initPdf: ${filePath.absolutePath} ")
        pdfDocument.close()
    }
}