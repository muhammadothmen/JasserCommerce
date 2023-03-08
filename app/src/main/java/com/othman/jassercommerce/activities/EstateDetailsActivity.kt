package com.othman.jassercommerce.activities

import android.os.Bundle
import com.othman.jassercommerce.R
import kotlinx.android.synthetic.main.activity_estate_details.*

class EstateDetailsActivity :BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estate_details)

        setupActionBar(toolbar_Estate_details_activity, "التفاصيل")
    }
}