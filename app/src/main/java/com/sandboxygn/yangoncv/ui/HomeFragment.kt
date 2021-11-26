package com.sandboxygn.yangoncv.ui

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.databinding.FragmentHomeBinding
import com.sandboxygn.yangoncv.model.CvViewModel
import java.util.*

class HomeFragment : Fragment() {
   /* private val ICON = "icon"*/
    private val LANGUAGE = "language"

    private val sharedViewModel: CvViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = requireActivity().getSharedPreferences("Setting", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        sharedPreferences.getString(LANGUAGE,"my")
            ?.let { setLanguage(requireActivity(), it) }
        var fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentHomeBinding



        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_burmese -> {
                item.setIcon(R.drawable.myanmarflag)
                setLanguage(requireActivity(), "my")
                requireActivity().recreate()
                true
            }
            R.id.menu_english -> {

                setLanguage(requireActivity(), "en")
                requireActivity().recreate()
                true
            }
            R.id.menu_about_us -> {
                true
            }
            R.id.menu_contact_us -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLanguage(activity: Activity, language: String) {
        editor.putString(LANGUAGE, language)
        editor.commit()
        var locale = Locale(language)
        var resources = activity.resources
        var configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeFragment = this
        binding.sharedViewModel = sharedViewModel
    }


    fun createCV() {
        sharedViewModel.resetData()
        Toast.makeText(context, "You are my sunshine", Toast.LENGTH_SHORT)
        findNavController().navigate(R.id.action_homeFragment_to_firstStepCreatingCvFragment)
    }


}