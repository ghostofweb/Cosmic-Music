package com.sahiljeet.musicPlayer

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 3000L // Duration in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Find the ImageView and TextView
        val splashIcon = findViewById<ImageView>(R.id.splash_icon)
        val splashText = findViewById<TextView>(R.id.splash_text)

        // 1. Bounce animation for the icon
        val bounceAnimation = ObjectAnimator.ofFloat(splashIcon, "translationY", 0f, -50f, 0f)
        bounceAnimation.duration = 800
        bounceAnimation.interpolator = BounceInterpolator()

        // 2. Rotate with slight tilt animation
        val rotateAnimation = ObjectAnimator.ofFloat(splashIcon, "rotation", 0f, 15f, -15f, 0f)
        rotateAnimation.duration = 1200
        rotateAnimation.startDelay = 800 // Starts after bounce
        rotateAnimation.interpolator = DecelerateInterpolator()

        // 3. Zoom out animation
        val scaleXAnimation = ObjectAnimator.ofFloat(splashIcon, "scaleX", 1f, 0f)
        val scaleYAnimation = ObjectAnimator.ofFloat(splashIcon, "scaleY", 1f, 0f)
        val zoomOutSet = AnimatorSet().apply {
            playTogether(scaleXAnimation, scaleYAnimation)
            duration = 800
            startDelay = 2000 // Starts after rotation
            interpolator = AccelerateInterpolator()
        }

        // 4. Fade in and fade out animation for the text
        val fadeInText = ObjectAnimator.ofFloat(splashText, "alpha", 0f, 1f)
        fadeInText.duration = 1000
        fadeInText.startDelay = 800

        val fadeOutText = ObjectAnimator.ofFloat(splashText, "alpha", 1f, 0f)
        fadeOutText.duration = 800
        fadeOutText.startDelay = 2200 // Starts as the zoom out begins

        // Combine animations
        val animatorSet = AnimatorSet().apply {
            playTogether(bounceAnimation, rotateAnimation, zoomOutSet, fadeInText, fadeOutText)
        }

        animatorSet.start()

        // Start the next activity after the animations
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}
