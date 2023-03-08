package com.othman.jassercommerce.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.othman.jassercommerce.R
import com.othman.jassercommerce.adapters.TabsPagerAdapter
import com.othman.jassercommerce.fragments.DemandsFragment
import com.othman.jassercommerce.fragments.HistoryFragment
import com.othman.jassercommerce.fragments.OffersFragment
import com.othman.jassercommerce.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var  openAddEstateActivity: ActivityResultLauncher<Intent>
    private val offersFragment = OffersFragment()
    private val demandsFragment = DemandsFragment()
    private val historyFragment = HistoryFragment()
    private var deal = Constants.OFFER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        splashScreenDelay()

        setupActionBar()

        setupTabsAdapter()

        addEstateLauncherSetup()

        nav_view.setNavigationItemSelectedListener(this)

        fab_add_estate.setOnClickListener {
            val addEstateIntent = Intent(this@MainActivity,AddEstateActivity::class.java)
            addEstateIntent.putExtra(Constants.DEAL_INTENT, deal)
            openAddEstateActivity.launch(addEstateIntent)
        }
    }

    private fun addEstateLauncherSetup() {
        openAddEstateActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
                if (result.resultCode == RESULT_OK ) {
                    if(deal == Constants.OFFER){
                        offersFragment.getHappyPlacesListFromLocalDB()
                    }else{
                        demandsFragment.getHappyPlacesListFromLocalDB()
                    }
                }else {
                    Log.e("Activity","canceled or back pressed")
                }
            }
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
    private fun setupTabsAdapter() {
        val tabsPagerAdapter = TabsPagerAdapter(this)
        // add fragment to the list
        tabsPagerAdapter.addFragment(offersFragment, resources.getString(R.string.tab_offers))
        tabsPagerAdapter.addFragment(demandsFragment, resources.getString(R.string.tab_demands))
        tabsPagerAdapter.addFragment(historyFragment, resources.getString(R.string.tab_history))
        // Adding the Adapter to the ViewPager
        viewPager.adapter = tabsPagerAdapter
        viewPager.currentItem = 0
        // bind the viewPager with the TabLayout.
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsPagerAdapter.getTabTitle(position)
        }.attach()
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(tabsPagerAdapter.getTabTitle(position)){
                    resources.getString(R.string.tab_offers) -> {
                        fab_add_estate.show()
                        deal = Constants.OFFER
                    }
                    resources.getString(R.string.tab_demands) -> {
                        fab_add_estate.show()
                        deal = Constants.DEMAND
                    }
                    resources.getString(R.string.tab_history) -> {
                        fab_add_estate.hide()
                        deal = Constants.HISTORY
                    }
                }

            }

             /*override fun onPageScrollStateChanged(state: Int) {
                 super.onPageScrollStateChanged(state)
                 Toast.makeText(this@MainActivity,"onPageScrollStateChangedTest",Toast.LENGTH_SHORT).show()
             }

             override fun onPageScrolled(
                 position: Int,
                 positionOffset: Float,
                 positionOffsetPixels: Int
             ) {
                 super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                 Toast.makeText(this@MainActivity,"onPageScrolledTest",Toast.LENGTH_SHORT).show()
             }*/
        })
    }

    /**
     * A function for opening and closing the Navigation Drawer.
     */
    private fun toggleDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {
        setSupportActionBar(toolbar_main_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_menu)
            actionBar.title = resources.getString(R.string.app_name)
        }
        toolbar_main_activity.setNavigationOnClickListener {
            toggleDrawer()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_profile -> {
                Toast.makeText(this, "navigation test",Toast.LENGTH_SHORT).show()
                //TODO edit profile implementation
            }

            R.id.nav_sign_out -> {
                Toast.makeText(this, "navigation test",Toast.LENGTH_SHORT).show()
                //TODO signOut implementation
            }

            R.id.nav_my_sign_in -> {
                Toast.makeText(this, "navigation test",Toast.LENGTH_SHORT).show()
                //TODO signIn implementation
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}