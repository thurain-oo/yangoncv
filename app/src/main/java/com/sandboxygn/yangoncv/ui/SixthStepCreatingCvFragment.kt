package com.sandboxygn.yangoncv.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.databinding.FragmentSixthStepCreatingCvBinding
import com.sandboxygn.yangoncv.model.CvViewModel

class SixthStepCreatingCvFragment : Fragment() {
    private val sharedViewModel : CvViewModel by activityViewModels()
    private lateinit var   binding:FragmentSixthStepCreatingCvBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSixthStepCreatingCvBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sixthStepFragment = this
        binding.sharedViewModel=sharedViewModel

    }

    fun goToNextStep(){
        sharedViewModel.setExpectedSalary(binding.editExpectedSalary.text.toString())
        sharedViewModel.setAddress(binding.editContactAddress.text.toString())
        sharedViewModel.setPhoneNo(binding.editPhoneNo.text.toString())
        sharedViewModel.setEmail(binding.editEmailAdd.text.toString())
        findNavController().navigate(R.id.action_sixthStepCreatingCvFragment_to_seventhStepCreatingCvFragment)
    }
    fun cancelCreatingCv(){
        sharedViewModel.resetData()
        findNavController().navigate(R.id.action_sixthStepCreatingCvFragment_to_homeFragment)
    }

}