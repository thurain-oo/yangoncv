package com.sandboxygn.yangoncv.ui

import com.sandboxygn.yangoncv.R


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.MobileAds


/**
 * Splash Screen with the app icon and name at the center, this is also the launch screen and
 * opens up in fullscreen mode. Once launched it waits for 2 seconds after which it opens the
 * MainActivityOriginal
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        makeFullScreen()

        setContentView(R.layout.activity_splash)

        MobileAds.initialize(this)

        // Using a handler to delay loading the MainActivityOriginal
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {

            // Start activity
            startActivity(Intent(this, MainActivity::class.java))

            // Animate the loading of new activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            // Close this activity
            finish()

        }
        handler.postDelayed(runnable, 2000)
    }

    private fun makeFullScreen() {
        // Remove Title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Make Fullscreen
        WindowCompat.getInsetsController(window, window.decorView)
            ?.hide(
                WindowInsetsCompat.Type.statusBars() or
                        WindowInsetsCompat.Type.navigationBars()
            )

        // Hide the toolbar
        supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}
