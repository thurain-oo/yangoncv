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
import android.content.Intent
import android.net.Uri

import android.os.Environment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import java.io.File
import java.lang.Exception


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

        sharedPreferences.getString(LANGUAGE, "my")
            ?.let { setLanguage(requireActivity(), it) }
        var fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentHomeBinding




        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //This method is called before onCreateOptionsMenu ,so if you want to change icon dynamically or contents, edit here

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        var flag = menu.findItem(R.id.language)
        if (sharedPreferences.getString(LANGUAGE, "my") == "my") {
            flag.setIcon(R.drawable.myanmarflag)
        } else {
            flag.setIcon(R.drawable.usaflag)
        }
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
            R.id.menu_feedback -> {
                getFeedback()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getFeedback() {
        val summary = "Hello, \n This is the feedback place about using this Yangon CVs app. "

        val addresses = arrayOf("mr.zxchero12@gmail.com")
        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_EMAIL, addresses)
            .putExtra(Intent.EXTRA_SUBJECT, "Feedback For Yangon CVs android app.")
            .putExtra(Intent.EXTRA_TEXT, summary)
            .setPackage("com.google.android.gm")
    try{
            startActivity(intent)

        }catch (e: Exception){
            Toast.makeText(context,"Oops! Your device does not have the Google Mail App",Toast.LENGTH_LONG).show()
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

    fun goCheckMyCvs(){


       findNavController().navigate(R.id.action_homeFragment_to_cvListFragment)
    }

}