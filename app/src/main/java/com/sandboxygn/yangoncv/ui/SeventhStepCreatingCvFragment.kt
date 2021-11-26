package com.sandboxygn.yangoncv.ui

import android.Manifest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.databinding.FragmentSeventhStepCreatingCvBinding
import com.sandboxygn.yangoncv.model.CvViewModel

import android.content.Intent
import android.content.pm.PackageManager

import android.net.Uri

import android.widget.Toast

import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat

import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

import android.content.Context

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract


class SeventhStepCreatingCvFragment : Fragment() {


    private lateinit var cropImageResultLauncher: ActivityResultLauncher<Any?>
    private val sharedViewModel: CvViewModel by activityViewModels()
    private lateinit var binding: FragmentSeventhStepCreatingCvBinding
    private lateinit var cameraPermission: Array<String>
    private val cropImageSActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(35, 45)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(context)
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {

            return CropImage.getActivityResult(intent)?.uri
        }
    }

    override fun onResume() {
        super.onResume()
        if (sharedViewModel.profileImage.value != null) {
            binding.imageView.setImageURI(sharedViewModel.profileImage.value)
        } else {
            binding.imageView.setImageResource(R.drawable.image_view_placeholder)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //https://www.youtube.com/watch?v=ATj6tq5HQZU
        cropImageResultLauncher = registerForActivityResult(cropImageSActivityResultContract) {
            it?.let { uri ->
                sharedViewModel.setProfileImage(uri)
                binding.imageView.setImageURI(uri)
            }
        }


        cameraPermission =
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        // Inflate the layout for this fragment
        binding = FragmentSeventhStepCreatingCvBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.seventhStepFragment = this
        binding.sharedViewModel = sharedViewModel

    }

    fun goToNextStep() {
        sharedViewModel.setWorkExp(binding.editExperience.text.toString())

        if (sharedViewModel.profileImage.value == null) {
            Toast.makeText(
                requireContext(),
                "Choose didn't choose any photo",
                Toast.LENGTH_LONG
            ).show()

        }else{
            findNavController().navigate(R.id.action_seventhStepCreatingCvFragment_to_summaryFragment)

        }
    }

    fun cancelCreatingCv() {
        sharedViewModel.resetData()
        findNavController().navigate(R.id.action_seventhStepCreatingCvFragment_to_homeFragment)
    }

    fun pickAnImage() {
        if (checkPermission() == false) {
            Toast.makeText(
                context,
                "Please Allow Camera and Storage Permissions.",
                Toast.LENGTH_SHORT
            ).show()

            requestPermission();
        } else {
            cropImageResultLauncher.launch(null)
        }
    }

    // checking camera and storage permissions
    fun checkPermission(): Boolean? {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val result1 = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        return result && result1
    }

    // Requesting camera  and storage permission
    fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity as MainActivity,
            cameraPermission,
            PackageManager.PERMISSION_GRANTED
        )
    }


}
