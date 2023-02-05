package com.othman.jassercommerce.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.othman.jassercommerce.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            finish()
        }else{
            setContentView(R.layout.activity_splash)
            Handler(Looper.myLooper()!!).postDelayed({
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                finish()
            },500)
        }
    }
}