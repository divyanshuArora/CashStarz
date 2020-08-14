package com.developers.CrbClub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.developers.CrbClub.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    lateinit var sharedPref: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        sharedPref = SharedPreferenceManager(this@SplashActivity)
        Glide.with(this).load(R.drawable.splash_screen).into(splashIcon)
        Handler().postDelayed(
                {
                    if (sharedPref.getString("FIRST").isNullOrEmpty()) {
                        startActivity<WelcomeScreenActivity>()
                        finish()
                    }
                     else {
                        startActivity<DashboardActivity>()
                        finish()
                    }

                }, 3000
        )
    }




}