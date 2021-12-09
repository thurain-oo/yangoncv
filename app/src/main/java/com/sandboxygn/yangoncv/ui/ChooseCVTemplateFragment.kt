package com.sandboxygn.yangoncv.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.adapter.TemplateAdapter
import com.sandboxygn.yangoncv.databinding.FragmentChooseCVTemplateBinding
import com.sandboxygn.yangoncv.model.CvViewModel
import com.sandboxygn.yangoncv.model.TemplateItem

class ChooseCVTemplateFragment : Fragment() {

    val sharedViewModel : CvViewModel by activityViewModels()
    private lateinit var binding:FragmentChooseCVTemplateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChooseCVTemplateBinding.inflate(inflater,container,false)

        val templateSet = loadTemplateItems()

        binding.recyclerViewCvTemplate.adapter= TemplateAdapter(this,templateSet)

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loadTemplateItems(): List<TemplateItem> {
        return listOf<TemplateItem>(
            TemplateItem(R.drawable.cv_template_placeholder,"simple"),
            TemplateItem(R.drawable.cv_template_placeholder,"simple"),
            TemplateItem(R.drawable.cv_template_placeholder,"simple"),
            TemplateItem(R.drawable.cv_template_placeholder,"simple"),
            TemplateItem(R.drawable.cv_template_placeholder,"simple"),
            TemplateItem(R.drawable.cv_template_placeholder,"simple"),
        )

    }

    fun goToNextScreen() { //CvViewModel.templateType is set in adapter onclick listener
        findNavController().navigate(R.id.action_chooseCVTemplateFragment_to_cvPreviewFragment)
    }



}