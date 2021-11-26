package com.sandboxygn.yangoncv.ui

import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.Path.Direction
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment.*
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.databinding.FragmentSummaryBinding
import com.sandboxygn.yangoncv.model.CvViewModel
import java.io.File
import java.io.FileOutputStream


class SummaryFragment : Fragment() {
    private lateinit var bitmap: Bitmap
    private lateinit var scaledBitmap:Bitmap
    private lateinit var binding: FragmentSummaryBinding
    private val sharedViewModel: CvViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ///TODO()
       // if (Build.VERSION.SDK_INT < 28) {


//
//       GlobalScope.launch {
//            if (Build.VERSION.SDK_INT < 28) {
//                Toast.makeText(context,"I am less than 28", Toast.LENGTH_SHORT).show()
//
//                bitmap = MediaStore.Images.Media.getBitmap(
//                    requireContext().contentResolver,
//                    sharedViewModel.profileImage.value
//                )
//            }
//            else {
//                Toast.makeText(context,"I am greater than 28", Toast.LENGTH_SHORT).show()
//                /// TODO: 11/23/2021 -- Find Alternative solutions for getBitmap
//                val source = ImageDecoder.createSource(
//                    requireContext().contentResolver,
//                    sharedViewModel.profileImage.value!!
//
//                )
//               bitmap = ImageDecoder.decodeBitmap(source)
//
//            }
//        }




        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        bitmap = MediaStore.Images.Media.getBitmap(
                requireContext().contentResolver,
                sharedViewModel.profileImage.value
            )

            binding.imageView.visibility=View.VISIBLE
            binding.imageView.setImageBitmap(bitmap)


        scaledBitmap  = Bitmap.createScaledBitmap(bitmap,70,90,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.summaryFragment = this
        binding.sharedViewModel = sharedViewModel
    }

    fun goToNextScreen() {
        if (!checkStoragePermission()) {
            Toast.makeText(context, "Allow Storage Permission.", Toast.LENGTH_SHORT)
                .show()
            requestStoragePermission()
        } else {
            createCv()
        }
        findNavController().navigate(R.id.action_summaryFragment_to_homeFragment)
       // findNavController().navigate(R.id.action_summaryFragment_to_cvListFragment)
    }

    // checking storage permissions
    private fun checkStoragePermission(): Boolean {

        return ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Requesting  storage permission
    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            activity as MainActivity,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PackageManager.PERMISSION_GRANTED
        )

    }


    private fun createCv() {

        val document = PdfDocument()
        //x 1 unit= 1/72 inch ,y 1unit = 1/72 , A4 Size = 8.27 x11.7 inch
        val pageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint()
        canvas.drawBitmap(scaledBitmap, (2 * pageInfo.pageWidth / 3) + 50.toFloat(), 40F, paint)
        paint.style= Paint.Style.STROKE
        paint.strokeWidth = 2F

        var path = Path()

        canvas.drawRect((2*pageInfo.pageWidth/3)+45.toFloat(),35F ,(2*pageInfo.pageWidth/3)+125.toFloat(),135F, paint)




        paint.style= Paint.Style.FILL
        paint.strokeWidth = 0F

        /**Text Size

        Header = 20F
        Normal Text = 14F
         */


        val firstColumnStartPosition = 40F
        //Paint Attributes are once applied , the same as it until it values changed again .
        //eg.paint.textSize
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 20F
        paint.isFakeBoldText = true
        canvas.drawText("Curriculum Vitae", (pageInfo.pageWidth / 2).toFloat(), 30F, paint)

        //Name
        var y = 155F
        paint.textAlign = Paint.Align.LEFT
        paint.isFakeBoldText = false
        paint.textSize = 14F
        canvas.drawText("Name", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.name.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20
        //Date Of Birth
        canvas.drawText("Date Of Birth", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.dateOfBirth.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Parents' Name
        canvas.drawText("Parents' Name", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.fatherName.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Race
        canvas.drawText("Race", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.race.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Nationality
        canvas.drawText("Nationality", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.nationality.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //N.R.C Number
        canvas.drawText("N.R.C Number", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.nrcNo.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Religion
        canvas.drawText("Religion", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.religion.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Sex
        canvas.drawText("Sex", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.sex.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Martial Status
        canvas.drawText("Martial Status", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.martialStatus.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Weight
        canvas.drawText("Weight", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.weight.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Height
        canvas.drawText("Height", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.height.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Language
        canvas.drawText("Language", firstColumnStartPosition, y, paint)
        if (sharedViewModel.skilledLangsEdited.isEmpty()) {
            canvas.drawText("- ",(pageInfo.pageWidth / 2) + 20.toFloat(),
                y,
                paint)
            y += 20
        }
        for (i in sharedViewModel.skilledLangsEdited) {

            canvas.drawText(
                "- $i",
                (pageInfo.pageWidth / 2) + 20.toFloat(),
                y,
                paint
            )
            y += 20
        }

        //Educational Qualifications
    /*    var bounds = Rect()
        paint.getTextBounds(sharedViewModel.educationalQualification.value.toString(),0,sharedViewModel.educationalQualification.value.toString().length,bounds)
        canvas.drawText(bounds.height().toString(),(pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint)
        y+=20*/

        //divide a long string to sub string delimited by \n and \r
        fun multilinetextIntoLines(a: String) {
            val lines = a.lines()
            lines.forEach { line ->
                canvas.drawText(
                    "- $line",
                    (pageInfo.pageWidth / 2) + 20.toFloat(),
                    y,
                    paint
                )
                y += 20
            }
        }
        canvas.drawText("Educational Qualifications", firstColumnStartPosition, y, paint)

        multilinetextIntoLines(sharedViewModel.educationalQualification.value.toString())

        //Others Qualifications and certificates
        canvas.drawText(
            "Others Qualifications and certificates",
            firstColumnStartPosition,
            y,
            paint
        )
        multilinetextIntoLines(sharedViewModel.certificates.value.toString())

        //Working Experience
        canvas.drawText("Working Experience", firstColumnStartPosition, y, paint)
        multilinetextIntoLines(sharedViewModel.workExp.value.toString())

        //Expected Salary
        canvas.drawText("Expected Salary", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.expectedSalary.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20


        //Contact Address
        canvas.drawText("Contact Address", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.contactAddress.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Contact Phone Number
        canvas.drawText("Contact Phone Number", firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.phoneNo.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
        y += 20

        //Contact Email Address
        canvas.drawText("Contact Email Address", firstColumnStartPosition, y, paint)

        canvas.drawText(
            "- " + sharedViewModel.email.value.toString(),
            (pageInfo.pageWidth / 2) + 20.toFloat(),
            y,
            paint
        )
         y += 20

        //x 1 unit= 1/72 inch ,y 1unit = 1/72 , A4 Size = 8.27 x11.7 inch
        //page.canvas.drawText(sharedViewModel.skilledLangsString, 10.0F,10.0F,paint)

        document.finishPage(page)


        val filePath = requireContext().getExternalFilesDir("/Yangon CVs")!!.absolutePath + "/" + sharedViewModel.name.value + "_" + System.currentTimeMillis() / 1000 + "CV.pdf"

        val file = File(filePath)

        try {
            document.writeTo(FileOutputStream(file))
            Toast.makeText(context, "CV created Successfully! Check ur cv under this app's package folder", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Creating CV failed! \nStorage Permission Allowance is needed.",
                Toast.LENGTH_LONG
            ).show()
        }
        document.close()


    }


    fun cancelCreatingCv() {
        sharedViewModel.resetData()
        findNavController().navigate(R.id.action_summaryFragment_to_homeFragment)
    }

}

