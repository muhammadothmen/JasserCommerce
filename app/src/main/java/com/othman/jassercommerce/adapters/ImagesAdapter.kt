package com.othman.jassercommerce.adapters

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.othman.jassercommerce.R
import kotlinx.android.synthetic.main.item_image.view.*


class ImagesAdapter(private val context: Context, private var list: ArrayList<Uri>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

   inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image , parent ,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder ){
            holder.itemView.iv_estate_image.setImageURI(list[position])
        }
    }


}