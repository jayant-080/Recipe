package com.jks.recipe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieDrawable
import com.jks.recipe.authUi.AuthActivity
import com.jks.recipe.mainUi.MainActivity
import kotlinx.android.synthetic.main.activity_splashscreen.*

class SplashScreen :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        splash_lottieAnimationView.repeatCount = LottieDrawable.INFINITE


        Handler(Looper.myLooper()!!).postDelayed({
            val gotoHomeScreen = Intent(this,MainActivity::class.java)
            startActivity(gotoHomeScreen)
            finish()
        },4000L)
    }
}