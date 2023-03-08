package com.othman.jassercommerce.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.othman.jassercommerce.R
import kotlinx.android.synthetic.main.activity_main.*

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    /**
     * A function to setup action bar for all activities
     * @param toolbar the toolbar inside the appBar.
     * @param title the title of action bar.
     */
    fun setupActionBar(toolbar :Toolbar, title: String) {

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = title
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_icon)
            toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }
    fun log(message: Any){
        Log.e("JC", message.toString())
    }

}