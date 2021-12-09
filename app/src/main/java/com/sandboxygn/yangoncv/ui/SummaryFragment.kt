package com.sandboxygn.yangoncv.ui

import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment.*
import android.provider.MediaStore
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.databinding.FragmentSummaryBinding
import com.sandboxygn.yangoncv.model.CvViewModel
import java.io.File
import java.io.FileOutputStream


class SummaryFragment : Fragment() {
    private lateinit var bitmap: Bitmap
    private lateinit var scaledBitmap: Bitmap
    private lateinit var binding: FragmentSummaryBinding
    private val sharedViewModel: CvViewModel by activityViewModels()
    private var mInterstitialAd: InterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null
    private final var TAG = "SummaryFragment"
    private lateinit var adRequest : AdRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //AD

        //Reward AD
         adRequest = AdRequest.Builder().build()


        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        bitmap = MediaStore.Images.Media.getBitmap(
            requireContext().contentResolver,
            sharedViewModel.profileImage.value
        )

        binding.imageView.visibility = View.VISIBLE
        binding.imageView.setImageBitmap(bitmap)

        if(sharedViewModel.feet.value != "" && sharedViewModel.inches.value != ""){
            val formattedHeight = getString(R.string.formatted_height,sharedViewModel.feet.value,sharedViewModel.inches.value)
            binding.textFormattedHeight.text = formattedHeight

        }else{
            binding.textFormattedWeight.text = ""
        }

        if(sharedViewModel.weight.value != ""){
            val formattedWeight = getString(R.string.formatted_weight,sharedViewModel.weight)
            binding.textFormattedWeight.text = formattedWeight
        }else{
            binding.textFormattedWeight.text = ""
        }

        scaledBitmap = Bitmap.createScaledBitmap(bitmap, 70, 90, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.summaryFragment = this
        binding.sharedViewModel = sharedViewModel
    }

    fun goToNextScreen() {
        if (!checkStoragePermission()) {
            Toast.makeText(context, "Allow Storage Permission.", Toast.LENGTH_SHORT)
                .show()
            requestStoragePermission()
        } else {
          //  showRewardAD()
          //  createCv()


        }
        showInterstitialAd()
         findNavController().navigate(R.id.action_summaryFragment_to_chooseCVTemplateFragment)
    }

    // checking storage permissions
    private fun checkStoragePermission(): Boolean {

        return ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Requesting  storage permission
    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            activity as MainActivity,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PackageManager.PERMISSION_GRANTED
        )

    }
    fun cancelCreatingCv() {
        sharedViewModel.resetData()
        findNavController().navigate(R.id.action_summaryFragment_to_homeFragment)
    }

    fun showInterstitialAd(){
        //Interstitial
        InterstitialAd.load(context,getString(R.string.real_intenstitial_), adRequest, object : InterstitialAdLoadCallback() {
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

