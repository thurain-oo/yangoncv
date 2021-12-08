package com.sandboxygn.yangoncv.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.databinding.FragmentFourthStepCreatingCvBinding
import com.sandboxygn.yangoncv.model.CvViewModel

class FourthStepCreatingCvFragment : Fragment() {

    private lateinit var binding: FragmentFourthStepCreatingCvBinding
    private val sharedViewModel: CvViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val burmeseSkillSet = resources.getStringArray(R.array.burmese_skill_set)
        val engSkillSet = resources.getStringArray(R.array.eng_skill_set)
        val thaiSkillSet = resources.getStringArray(R.array.thai_skill_set)
        val japaneseSkillSet = resources.getStringArray(R.array.japanese_skill_set)
        val chineseSkillSet = resources.getStringArray(R.array.chinese_skill_set)
        binding = FragmentFourthStepCreatingCvBinding.inflate(inflater, container, false)

        binding.spinnerMyanmar.adapter= ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,burmeseSkillSet)
        binding.spinnerEnglish.adapter= ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,engSkillSet)
        binding.spinnerChinese.adapter= ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,chineseSkillSet)
        binding.spinnerJapanese.adapter= ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,japaneseSkillSet)
        binding.spinnerThai.adapter= ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,thaiSkillSet)



        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
       addLangSkill()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fourthStepFragment = this
        binding.sharedViewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }

    fun addLangSkill() {
        //Japanese
        if (binding.checkboxJapanese.isChecked ) {
            binding.spinnerJapanese.isVisible = true
            if(!sharedViewModel.skilledLangs.contains("Japanese"))
            sharedViewModel.skilledLangs.add("Japanese")
        }else {
            binding.spinnerJapanese.isVisible = false
            sharedViewModel.skilledLangs.remove("Japanese")
        }

        //Myanmar
        if (binding.checkboxMyanmar.isChecked ){
            binding.spinnerMyanmar.isVisible = true
            if(!sharedViewModel.skilledLangs.contains("Myanmar"))
            sharedViewModel.skilledLangs.add("Myanmar")}
        else {
            binding.spinnerMyanmar.isVisible = false
            sharedViewModel.skilledLangs.remove("Myanmar")
        }
        //Thai
        if (binding.checkboxThai.isChecked ) {
            binding.spinnerThai.isVisible = true

            if(!sharedViewModel.skilledLangs.contains("Thai"))
            sharedViewModel.skilledLangs.add("Thai")
        }else {
            binding.spinnerThai.isVisible = false
            sharedViewModel.skilledLangs.remove("Thai")
        }
        //Chinese
        if (binding.checkboxChinese.isChecked ) {
            binding.spinnerChinese.isVisible = true
            if (!sharedViewModel.skilledLangs.contains("Chinese"))
                sharedViewModel.skilledLangs.add("Chinese")
        }else {
            binding.spinnerChinese.isVisible = false
            sharedViewModel.skilledLangs.remove("Chinese")
        }
        //English
        if (binding.checkBoxEnglish.isChecked  ) {
            binding.spinnerEnglish.isVisible = true

            if(!sharedViewModel.skilledLangs.contains("English"))
            sharedViewModel.skilledLangs.add("English")
        }else {
            binding.spinnerEnglish.isVisible = false
            sharedViewModel.skilledLangs.remove("English")
        }


        }

    fun goToNextStep() {
     //   setUncheckedSpinnersInvisibleOnNextVisit()
        sharedViewModel.setAddOnLang(binding.editAddOnSkillLevel.text.toString())
        sharedViewModel.setAddOnLangSkill(binding.editAddOnSkillLevel.text.toString())

        if (sharedViewModel.skilledLangs.isEmpty() && binding.editAnotherLang.text.toString() == "") {

                Toast.makeText(
                    context,
                    "You didn't choose any language as skill.",
                    Toast.LENGTH_LONG
                ).show()
            sharedViewModel.skilledLangsString = ""
        } else {
            sharedViewModel.setAddOnLang(binding.editAnotherLang.text.toString())
            sharedViewModel.setAddOnLangSkill(binding.editAddOnSkillLevel.text.toString())
            editSkillLangs()
            sharedViewModel.skilledLangsString = sharedViewModel.skilledLangsEdited.distinct().sorted().joinToString(separator = ", \n")
        }
        findNavController().navigate(R.id.action_fourthStepCreatingCvFragment_to_fifthStepCreatingCvFragment)
    }

    private fun editSkillLangs(){

        //To avoid adding more than 1 skill level in summary , before we update , intialize the list
        sharedViewModel.skilledLangsEdited= mutableListOf()
        if(sharedViewModel.skilledLangs.contains("Myanmar") && !sharedViewModel.skilledLangsEdited.contains("Myanmar"))
            sharedViewModel.skilledLangsEdited.add( "Myanmar \t-\t"+binding.spinnerMyanmar.selectedItem.toString())
        if(sharedViewModel.skilledLangs.contains("English") && !sharedViewModel.skilledLangsEdited.contains("English"))
            sharedViewModel.skilledLangsEdited.add( "English \t- \t"+binding.spinnerEnglish.selectedItem.toString())
        if(sharedViewModel.skilledLangs.contains("Thai"))
            sharedViewModel.skilledLangsEdited.add( "Thai \t-\t"+binding.spinnerThai.selectedItem.toString())
        if(sharedViewModel.skilledLangs.contains("Japanese") && !sharedViewModel.skilledLangsEdited.contains("Japanese"))
            sharedViewModel.skilledLangsEdited.add( "Japanese\t-\t"+binding.spinnerJapanese.selectedItem.toString())
        if(sharedViewModel.skilledLangs.contains("Chinese") && !sharedViewModel.skilledLangsEdited.contains("Chinese"))
            sharedViewModel.skilledLangsEdited.add( "Chinese \t-\t"+binding.spinnerChinese.selectedItem.toString())


        if(sharedViewModel.addOnLang.value != ""){
            sharedViewModel.skilledLangsEdited.add(sharedViewModel.addOnLang.value.toString()+" \t-\t"+sharedViewModel.addOnLangSkill.value.toString())
        }

    }

    fun cancelCreatingCv(){
        sharedViewModel.resetData()
        findNavController().navigate(R.id.action_fourthStepCreatingCvFragment_to_homeFragment)
    }

}