package com.sandboxygn.yangoncv.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.adapter.CvAdapter
import com.sandboxygn.yangoncv.adapter.OnPdfItemSelectListener

import com.sandboxygn.yangoncv.databinding.FragmentCvListBinding
import com.sandboxygn.yangoncv.model.CvViewModel
import java.io.File


class CvListFragment  : Fragment(),OnPdfItemSelectListener  {
    private lateinit var cvAdapter : CvAdapter
    private lateinit var cvList : List<File>

    private val sharedViewModel : CvViewModel by activityViewModels()
    private lateinit var binding :FragmentCvListBinding

    private lateinit var adRequest : AdRequest

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "CVListFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     //   resources.assets.
        binding= FragmentCvListBinding.inflate(inflater,container,false)
        displayPdf()


        //AD

        adRequest = AdRequest.Builder().build()

        //Banner
        binding.adView.loadAd(adRequest)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun listDirectory(directory:File) : ArrayList<File>{
        val arrayList = arrayListOf<File>()
        val files = directory.listFiles()
        for (file in  files){
            if(file.isDirectory && !file.isHidden){
                arrayList.addAll(listDirectory(file))
            }else{
                if(file.name.endsWith(".pdf")){
                    arrayList.add(file)
                }
            }
        }
    return arrayList
    }

    private fun displayPdf(){
         binding.recyclerViewCvList.setHasFixedSize(true)
        cvList= listDirectory(requireActivity().getExternalFilesDir(null)!!)
        cvAdapter = CvAdapter(this,cvList,this)
        binding.recyclerViewCvList.adapter =  cvAdapter
    }

    override fun onPdfSelected(file: File) {
        sharedViewModel.setPdfFile(file.absolutePath)
        showInterstitialAd()
      //  Toast.makeText(context,file.absolutePath ,Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_cvListFragment_to_pdfViewerFragment)
    }

    fun showInterstitialAd(){
        //Interstitial
            InterstitialAd.load(context,"ca-app-pub-2678792119822785/5575239279", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }

        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this.activity)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }
}