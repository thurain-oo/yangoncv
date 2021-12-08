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
import com.sandboxygn.yangoncv.databinding.FragmentFirstStepCreatingCvBinding
import com.sandboxygn.yangoncv.model.CvViewModel

class FirstStepCreatingCvFragment : Fragment() {
    private val sharedViewModel : CvViewModel by activityViewModels()
    private lateinit var binding : FragmentFirstStepCreatingCvBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(false) //to hide option menus in fragments except home fragment
        val fragmentFirstStepCreatingCvBinding = FragmentFirstStepCreatingCvBinding.inflate(inflater,container,false  )
        binding = fragmentFirstStepCreatingCvBinding

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.firstStepFragment =this
        binding.sharedViewModel = sharedViewModel
    }


    fun goToNextStep(){
        //Set Name , Date of Birth , and Father's Name
        sharedViewModel.setName(binding.editName.text.toString())
        sharedViewModel.setDateOfBirth(binding.editDateOfBirth.text.toString())
        sharedViewModel.setFatherName(binding.editFatherName.text.toString())

        findNavController().navigate(R.id.action_firstStepCreatingCvFragment_to_secondStepCreatingCvFragment)
    }

    fun cancelCreatingCv(){
        sharedViewModel.resetData()
        findNavController().navigate(R.id.action_firstStepCreatingCvFragment_to_homeFragment)
    }

}