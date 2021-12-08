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
import com.sandboxygn.yangoncv.databinding.FragmentThirdStepCreatingCvBinding
import com.sandboxygn.yangoncv.model.CvViewModel

class ThirdStepCreatingCvFragment : Fragment() {

    private lateinit var binding : FragmentThirdStepCreatingCvBinding
    private val sharedViewModel : CvViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdStepCreatingCvBinding.inflate(inflater,container,false)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.thirdStepFragment = this
        binding.sharedViewModel=sharedViewModel

    }

    fun goToNextStep(){
        //Set Religion , Sex , Martial Status , Weight , and Height
        sharedViewModel.setReligion(binding.editReligion.text.toString())
        sharedViewModel.setSex(binding.editSex.text.toString())
        sharedViewModel.setMartialStatus(binding.editMartialStatus.text.toString())
        sharedViewModel.setWeight(binding.editWeight.text.toString())
        sharedViewModel.setHeightfeet(binding.editHeightFoot.text.toString())
        sharedViewModel.setHeightInches(binding.editHeightInches.text.toString())

        findNavController().navigate(R.id.action_thirdStepCreatingCvFragment_to_fourthStepCreatingCvFragment)
    }

    fun cancelCreatingCv(){
        sharedViewModel.resetData()
        findNavController().navigate(R.id.action_thirdStepCreatingCvFragment_to_homeFragment)
    }
}