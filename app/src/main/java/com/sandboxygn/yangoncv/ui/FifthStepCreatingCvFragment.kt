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
import com.sandboxygn.yangoncv.databinding.FragmentFifthStepCreatingCvBinding
import com.sandboxygn.yangoncv.model.CvViewModel

class FifthStepCreatingCvFragment : Fragment() {
    private val sharedViewModel : CvViewModel by activityViewModels()
    private lateinit var binding: FragmentFifthStepCreatingCvBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFifthStepCreatingCvBinding.inflate(inflater,container,false)


        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fifthStepFragment= this
        binding.sharedViewModel=sharedViewModel


    }

    fun goToNextStep(){
        sharedViewModel.setEducationalQualification(binding.editEduQualificaition.text.toString())
        sharedViewModel.setCertificates(binding.editCertificates.text.toString())
        findNavController().navigate(R.id.action_fifthStepCreatingCvFragment_to_sixthStepCreatingCvFragment)
    }

    fun cancelCreatingCv(){
        sharedViewModel.resetData()
        findNavController().navigate(R.id.action_fifthStepCreatingCvFragment_to_homeFragment)
    }

}