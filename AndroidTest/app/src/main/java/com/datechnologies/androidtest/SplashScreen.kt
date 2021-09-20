package com.datechnologies.androidtest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class SplashScreen : Activity() {

    var Splash_Time_Out = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Handler().postDelayed(
                {
                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    finish()
                }, Splash_Time_Out.toLong())
    }
}