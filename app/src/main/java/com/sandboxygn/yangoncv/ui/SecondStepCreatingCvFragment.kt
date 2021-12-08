package com.sandboxygn.yangoncv.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.databinding.FragmentSecondStepCreatingCvBinding
import com.sandboxygn.yangoncv.model.CvViewModel

class SecondStepCreatingCvFragment : Fragment() {
    private val sharedViewModel : CvViewModel by activityViewModels()
    private lateinit var binding : FragmentSecondStepCreatingCvBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSecondStepCreatingCvBinding.inflate(inflater,container,false)


        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.secondStepFragment =this
        binding.sharedViewModel=sharedViewModel
    }

    fun goToNextStep(){
        //Set race , nationality and RNC No.
        sharedViewModel.setRace(binding.editRace.text.toString())
        sharedViewModel.setNationality(binding.editNationality.text.toString())
        sharedViewModel.setNrcNo(binding.editNrcNo.text.toString())
        findNavController().navigate(R.id.action_secondStepCreatingCvFragment_to_thirdStepCreatingCvFragment)
    }

    fun cancelCreatingCv(){
        sharedViewModel.resetData()
        findNavController().navigate(R.id.action_secondStepCreatingCvFragment_to_homeFragment)
    }

}