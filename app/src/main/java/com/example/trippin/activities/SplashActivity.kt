package com.example.trippin.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trippin.R

class SplashActivity : AppCompatActivity() {
    // Variables
    private var topAnim: Animation? = null
    private var bottomAnim: Animation? = null
    private var rightToCenter: Animation? = null
    private val handler: Handler = Handler()
    private val myRunnable = MyRunnable()
    private var appTitle: TextView? = null
    private var tagLine: TextView? = null
    private var fadeOut: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initAnimation()
        initView()
        sendToNextActivity()
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
    }

    private fun initView() {
        appTitle = findViewById(R.id.title)
        tagLine = findViewById(R.id.tag_line)

        // Setting the Animation
        appTitle?.startAnimation(topAnim)
        tagLine?.startAnimation(bottomAnim)

        // Start the next activity after animations finish
        rightToCenter?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                handler.postDelayed(myRunnable, 2000) // Delay a bit for animations to complete
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    private fun initAnimation() {
        // Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)
        rightToCenter = AnimationUtils.loadAnimation(this, R.anim.right_to_center)
    }

    private fun sendToNextActivity() {
        handler.postDelayed(myRunnable, 2000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        handler.removeCallbacks(myRunnable)
        finish()
    }

    private inner class MyRunnable : Runnable {
        override fun run() {
            val pairs: Array<Pair<View, String>> = arrayOf(
                Pair(appTitle!!, "app_title"),
                Pair(tagLine!!, "tag_line")
            )

            val intent = Intent(this@SplashActivity, SignInActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this@SplashActivity, *pairs
            )
            startActivity(intent, options.toBundle())
            onBackPressed()
        }
    }
}