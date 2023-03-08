package com.othman.jassercommerce.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.othman.jassercommerce.R
import com.othman.jassercommerce.models.Estate
import com.othman.jassercommerce.models.EstateModel
import kotlinx.android.synthetic.main.item_estate.view.*
import kotlinx.android.synthetic.main.item_image.view.*

class EstateItemsAdapter(private val context: Context?, private var list: ArrayList<EstateModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    private var onClickListener: OnClickListener? = null

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

            if (!estate.images.isNullOrEmpty()){
                Glide
                    .with(context!!)
                    .load(estate.images[0])
                    .placeholder(R.drawable.estate_item_place_holder)
                    .into(holder.itemView.civ_estate_image)
            }
            holder.itemView.tv_estate_item_primary.text = estate.type
            holder.itemView.tv_estate_item_secondary.text = estate.contract

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, estate)
                }
            }

        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, estate: EstateModel)
    }
}