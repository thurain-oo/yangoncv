package com.sandboxygn.yangoncv.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.sandboxygn.yangoncv.databinding.FragmentPdfViewerBinding
import com.sandboxygn.yangoncv.model.CvViewModel
import java.io.File

class PdfViewerFragment : Fragment() {
    private val sharedViewModel: CvViewModel by activityViewModels()
    private lateinit var binding: FragmentPdfViewerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Lets add those code to share pdf
        //Diagnostic
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()




        binding = FragmentPdfViewerBinding.inflate(inflater, container, false)

        binding.pdfView.fromFile(File(sharedViewModel.pdfFile.value)).load()
        binding.textPathOfCv.text = "Your CV is saved as: " + sharedViewModel.pdfFile.value

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sharedViewModel = sharedViewModel
        binding.fabSharePdf.setOnClickListener {
            sharePdftoOtherApps()
        }
    }

    fun sharePdftoOtherApps() {
        val file = File(sharedViewModel.pdfFile.value)
        if (!file.exists()) {
            Toast.makeText(context, "File does not exist!", Toast.LENGTH_LONG).show()

        } else {
            val intent = Intent(Intent.ACTION_SEND)
                .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
                .putExtra(Intent.EXTRA_TEXT,"This is my CV.")
                .setType("application/pdf")

            startActivity(Intent.createChooser(intent,"Share the CV pdf with...  "))
        }



    }

}