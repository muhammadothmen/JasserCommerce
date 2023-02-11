package com.othman.jassercommerce.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.othman.jassercommerce.R
import com.othman.jassercommerce.adapters.EstateItemsAdapter
import com.othman.jassercommerce.models.Estate
import kotlinx.android.synthetic.main.fragment_all.*


class HistoryFragment : Fragment(R.layout.fragment_all) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //recycleViewSetup()
    }


    private fun recycleViewSetup(){
        val list = arrayListOf(Estate("بيت","بيع"),
            Estate("مزرعة","اجار"),
            Estate("فيلا","رهنية"),
            Estate("بيت","بيع"),
            Estate("مزرعة","اجار"),
            Estate("فيلا","رهنية"),
            Estate("بيت","بيع"),
            Estate("مزرعة","اجار"),
            Estate("فيلا","رهنية"),
            Estate("بيت","بيع"),
            Estate("مزرعة","اجار"),
            Estate("فيلا","رهنية")
        )
        tv_no_estates.visibility = View.INVISIBLE
        rv_estate.visibility = View.VISIBLE
        val adapter = EstateItemsAdapter(activity,list)
        rv_estate.layoutManager = LinearLayoutManager(activity)
        rv_estate.setHasFixedSize(true)
        rv_estate.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv_estate.addItemDecoration(dividerItemDecoration)
    }

}