package com.othman.jassercommerce.activities

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import com.google.android.material.tabs.TabLayoutMediator
import com.othman.jassercommerce.R
import com.othman.jassercommerce.adapters.TabsPagerAdapter
import com.othman.jassercommerce.fragments.DemandsFragment
import com.othman.jassercommerce.fragments.HistoryFragment
import com.othman.jassercommerce.fragments.OffersFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        splashScreenDelay()

        setupActionBar(toolbar_main_activity,
            resources.getString(R.string.app_name),
            false)

        setupTabsAdapter()

    }


    /**
     * A function to delay app starting for splash screen view
     */
    private fun splashScreenDelay() {
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

    /**
     * A function to setup tabLayout with pagerAdapter
     */
    private fun setupTabsAdapter(){
        val tabsPagerAdapter = TabsPagerAdapter(this)
        // add fragment to the list
        tabsPagerAdapter.addFragment(OffersFragment(), resources.getString(R.string.tab_offers))
        tabsPagerAdapter.addFragment(DemandsFragment(), resources.getString(R.string.tab_demands))
        tabsPagerAdapter.addFragment(HistoryFragment(), resources.getString(R.string.tab_history))
        // Adding the Adapter to the ViewPager
        viewPagerMainActivity.adapter = tabsPagerAdapter
        viewPagerMainActivity.currentItem = 0
        // bind the viewPager with the TabLayout.
        TabLayoutMediator(tabLayoutMainActivity, viewPagerMainActivity) { tab, position ->
            tab.text = tabsPagerAdapter.getTabTitle(position)
        }.attach()
    }
}