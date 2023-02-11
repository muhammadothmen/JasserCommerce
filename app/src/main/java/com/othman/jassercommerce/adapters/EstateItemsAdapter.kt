package com.othman.jassercommerce.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.othman.jassercommerce.R
import com.othman.jassercommerce.models.Estate
import kotlinx.android.synthetic.main.item_estate.view.*

class EstateItemsAdapter(private val context: Context?, private var list: ArrayList<Estate>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_estate,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val estate = list[position]

        if (holder is MyViewHolder){
            holder.itemView.tv_estate_item_primary.text = estate.type
            holder.itemView.tv_estate_item_secondary.text = estate.contract
        }
    }
}