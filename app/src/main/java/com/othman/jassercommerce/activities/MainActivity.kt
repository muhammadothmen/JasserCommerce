package com.othman.jassercommerce.activities

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import com.othman.jassercommerce.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    Thread.sleep(1500)
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            })
    }
}