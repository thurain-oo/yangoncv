package com.sandboxygn.yangoncv.ui

import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment.*
import android.provider.MediaStore
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.databinding.FragmentSummaryBinding
import com.sandboxygn.yangoncv.model.CvViewModel
import java.io.File
import java.io.FileOutputStream


class SummaryFragment : Fragment() {
    private lateinit var bitmap: Bitmap
    private lateinit var scaledBitmap: Bitmap
    private lateinit var binding: FragmentSummaryBinding
    private val sharedViewModel: CvViewModel by activityViewModels()

    private var mRewardedAd: RewardedAd? = null
    private final var TAG = "SummaryFragment"
    private lateinit var adRequest : AdRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //AD
        //Reward AD
         adRequest = AdRequest.Builder().build()
        RewardedAd.load(context,"ca-app-pub-2678792119822785/1327381815", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                mRewardedAd = rewardedAd
            }
        })

        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        bitmap = MediaStore.Images.Media.getBitmap(
            requireContext().contentResolver,
            sharedViewModel.profileImage.value
        )

        binding.imageView.visibility = View.VISIBLE
        binding.imageView.setImageBitmap(bitmap)

        if(sharedViewModel.feet.value != "" && sharedViewModel.inches.value != ""){
            val formattedHeight = getString(R.string.formatted_height,sharedViewModel.feet.value,sharedViewModel.inches.value)
            binding.textFormattedHeight.text = formattedHeight
        }else{
            binding.textFormattedWeight.text = ""
        }

        if(sharedViewModel.weight.value != ""){
            val formattedWeight = getString(R.string.formatted_weight,sharedViewModel.weight)
            binding.textFormattedWeight.text = formattedWeight
        }else{
            binding.textFormattedWeight.text = ""
        }

        scaledBitmap = Bitmap.createScaledBitmap(bitmap, 70, 90, false)
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
            showRewardAD()
         /*   createCv()*/
        }

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
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2F

        canvas.drawRect(
            (2 * pageInfo.pageWidth / 3) + 45.toFloat(),
            35F,
            (2 * pageInfo.pageWidth / 3) + 125.toFloat(),
            135F,
            paint
        )

        paint.style = Paint.Style.FILL
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

        paint.typeface = Typeface.SERIF
        paint.isFakeBoldText = true
        canvas.drawText("Curriculum Vitae", (pageInfo.pageWidth / 2).toFloat(), 30F, paint)

        //Name
        var y = 155F
        paint.textAlign = Paint.Align.LEFT
        paint.isFakeBoldText = false
        paint.textSize = 14F
        canvas.drawText(resources.getString(R.string.name), firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.name.value.toString(),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25
        //Date Of Birth
        canvas.drawText(
            resources.getString(R.string.date_of_birth),
            firstColumnStartPosition,
            y,
            paint
        )
        canvas.drawText(
            "- " + sharedViewModel.dateOfBirth.value.toString(),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25

        //Parents' Name
        canvas.drawText(
            resources.getString(R.string.parents_name),
            firstColumnStartPosition,
            y,
            paint
        )
        canvas.drawText(
            "- " + sharedViewModel.fatherName.value.toString(),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25

        //Race
        canvas.drawText(resources.getString(R.string.race), firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.race.value.toString(),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25

        //Nationality
        canvas.drawText(
            resources.getString(R.string.nationality),
            firstColumnStartPosition,
            y,
            paint
        )
        canvas.drawText(
            "- " + sharedViewModel.nationality.value.toString(),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25

        //N.R.C Number
        canvas.drawText(resources.getString(R.string.nrc_no), firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.nrcNo.value.toString(),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25

        //Religion
        canvas.drawText(resources.getString(R.string.religion), firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.religion.value.toString(),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25

        //Sex
        canvas.drawText(resources.getString(R.string.sex), firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.sex.value.toString(),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25

        //Martial Status
        canvas.drawText(
            resources.getString(R.string.martial_status),
            firstColumnStartPosition,
            y,
            paint
        )
        canvas.drawText(
            "- " + sharedViewModel.martialStatus.value.toString(),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25

        //Weight
        canvas.drawText(resources.getString(R.string.weight), firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.weight.value.toString(),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25

        //Height
        canvas.drawText(resources.getString(R.string.height), firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- " + sharedViewModel.feet.value.toString()+getString(R.string.height_feet)+sharedViewModel.inches.value.toString()+getString(R.string.height_iches),
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )
        y += 25

        //Language
        canvas.drawText(resources.getString(R.string.language), firstColumnStartPosition, y, paint)
        if (sharedViewModel.skilledLangsEdited.isEmpty()) {
            canvas.drawText(
                "- ", (pageInfo.pageWidth / 2).toFloat(),
                y,
                paint
            )
            y += 25
        }
        for (i in sharedViewModel.skilledLangsEdited) {

            canvas.drawText(
                "- $i",
                (pageInfo.pageWidth / 2).toFloat(),
                y,
                paint
            )
            y += 25
        }

        //Educational Qualifications
        //pageInfo.pageWidth is the same as canvas.width


        canvas.drawText(
            resources.getString(R.string.educational_qualification),
            firstColumnStartPosition,
            y,
            paint
        )
        canvas.drawText(
            "- ",
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )

        val textPaint = TextPaint()
        textPaint.setTypeface(Typeface.SERIF)
        textPaint.textSize = 14F
        y = y - 15
        val textCanvas = page.canvas
        textCanvas.save()
        textCanvas.translate((pageInfo.pageWidth / 2) + 14.toFloat(), y) //specifying textlayout's starting point

        val eduTextLayout = StaticLayout(
            sharedViewModel.educationalQualification.value.toString(),
            textPaint,
            (canvas.width / 2) - 40,
            Layout.Alignment.ALIGN_NORMAL,
            1.0F,
            14.0F,
            false
        )


        eduTextLayout.draw(textCanvas)
        y = y + eduTextLayout.height.toFloat()
        canvas.restore()
        y += 25


        //Achievements and certificates
        canvas.drawText(
            resources.getString(R.string.achievements_and_certificates),
            firstColumnStartPosition,
            y,
            paint
        )
        canvas.drawText(
            "- ",
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )



        //User Input Wrappable text block
        y = y - 15
        textCanvas.save()
        val certsTextLayout = StaticLayout(
            sharedViewModel.certificates.value.toString(),
            textPaint,
            (canvas.width / 2) - 40,
            Layout.Alignment.ALIGN_NORMAL,
            1.0F,
            14.0F,
            false
        )


        textCanvas.translate((pageInfo.pageWidth / 2) + 14.toFloat(), y)
        certsTextLayout.draw(textCanvas)
        y = y + certsTextLayout.height.toFloat()
        canvas.restore()
        y += 25


        //Working Experience
        canvas.drawText(resources.getString(R.string.work_experience), firstColumnStartPosition, y, paint)
        canvas.drawText(
            "- ",
            (pageInfo.pageWidth / 2).toFloat(),
            y,
            paint
        )

        //User Input Wrappable text block
        y = y - 15
        textCanvas.save()
        val expTextLayout = StaticLayout(
            sharedViewModel.workExp.value.toString(),
            textPaint,
            (canvas.width / 2) - 40,
            Layout.Alignment.ALIGN_NORMAL,
            1.0F,
            14.0F,
            false
        )


        textCanvas.translate((pageInfo.pageWidth / 2) + 14.toFloat(), y)
        expTextLayout.draw(textCanvas)
        y = y + certsTextLayout.height.toFloat()
        canvas.restore()
        y += 25

         //Expected Salary
         canvas.drawText(resources.getString(R.string.expected_salary), firstColumnStartPosition, y, paint)
         canvas.drawText(
             "- " + sharedViewModel.expectedSalary.value.toString(),
             (pageInfo.pageWidth / 2) .toFloat(),
             y,
             paint
         )
         y += 25


         //Contact Address
         canvas.drawText(resources.getString(R.string.contact_address), firstColumnStartPosition, y, paint)
         canvas.drawText(
             "- " + sharedViewModel.contactAddress.value.toString(),
             (pageInfo.pageWidth / 2) .toFloat(),
             y,
             paint
         )
         y += 25

         //Contact Phone Number
         canvas.drawText(resources.getString(R.string.phone_no), firstColumnStartPosition, y, paint)
         canvas.drawText(
             "- " + sharedViewModel.phoneNo.value.toString(),
             (pageInfo.pageWidth / 2) .toFloat(),
             y,
             paint
         )
         y += 25

         //Contact Email Address
         canvas.drawText(resources.getString(R.string.email_address), firstColumnStartPosition, y, paint)

         canvas.drawText(
             "- " + sharedViewModel.email.value.toString(),
             (pageInfo.pageWidth / 2) .toFloat(),
             y,
             paint
         )
          y += 25
        //x 1 unit= 1/72 inch ,y 1unit = 1/72 , A4 Size = 8.27 x11.7 inch
        //page.canvas.drawText(sharedViewModel.skilledLangsString, 10.0F,10.0F,paint)

        document.finishPage(page)

        val filePath =
            requireContext().getExternalFilesDir(null)!!.absolutePath + "/" + sharedViewModel.name.value + "_" + System.currentTimeMillis() / 60000 + "CV.pdf"

        val file = File(filePath)
        val uri = file.toUri()

        try {
            sharedViewModel.setPdfFile(filePath)
            document.writeTo(FileOutputStream(file))
            Toast.makeText(context, "CV created Successfully! ", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Creating CV failed! \nCheck your storage permissions",
                Toast.LENGTH_LONG
            ).show()
        }
        document.close()
    }


    fun cancelCreatingCv() {
        sharedViewModel.resetData()
        findNavController().navigate(R.id.action_summaryFragment_to_homeFragment)
    }


    fun showRewardAD(){


        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Toast.makeText(context,"Watch the video till the end to get the CV.",Toast.LENGTH_LONG).show()
                Log.d(TAG, "Ad was shown.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                // Called when ad fails to show.
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad was dismissed.")
              //  mRewardedAd = null
            }
        }

        if (mRewardedAd != null) {
            mRewardedAd?.show(this.activity, OnUserEarnedRewardListener() {
                fun onUserEarnedReward(rewardItem: RewardItem) {

                    createCv()
                    findNavController().navigate(R.id.action_summaryFragment_to_pdfViewerFragment)

                    Log.d(TAG, "User earned the reward.")
                }
            })
        } else {
            Toast.makeText(context,"Watch the video till the end to get the CV.",Toast.LENGTH_LONG).show()
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }
    }


}

