package com.othman.jassercommerce.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.othman.jassercommerce.R
import com.othman.jassercommerce.adapters.EstateItemsAdapter
import com.othman.jassercommerce.databases.LocalDatabaseHandler
import com.othman.jassercommerce.models.Estate
import com.othman.jassercommerce.models.EstateModel
import kotlinx.android.synthetic.main.fragment_all.*


class DemandsFragment : Fragment(R.layout.fragment_all) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHappyPlacesListFromLocalDB()
    }

    internal fun getHappyPlacesListFromLocalDB(){
        val dbHandler = LocalDatabaseHandler(activity)
        val estatesList  = dbHandler.getEstatesList()

        if (estatesList!!.size > 0) {
            rv_estate.visibility = View.VISIBLE
            tv_no_estates.visibility = View.GONE
            recycleViewSetup(estatesList)
        } else {
            rv_estate.visibility = View.GONE
            tv_no_estates.visibility = View.VISIBLE
        }
    }


    private fun recycleViewSetup(estateList: ArrayList<EstateModel>){

        tv_no_estates.visibility = View.INVISIBLE
        rv_estate.visibility = View.VISIBLE
        val adapter = EstateItemsAdapter(activity,estateList)
        rv_estate.layoutManager = LinearLayoutManager(activity)
        rv_estate.setHasFixedSize(true)
        rv_estate.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv_estate.addItemDecoration(dividerItemDecoration)
    }

}