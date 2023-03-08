package com.othman.jassercommerce.adapters

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.othman.jassercommerce.R
import com.othman.jassercommerce.models.EstateModel
import kotlinx.android.synthetic.main.item_image.view.*


class ImagesAdapter(private val context: Context, private var list: ArrayList<Uri>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image , parent ,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder ){

            if (list[position] == estateImagePlaceHolderUri()){
                Glide
                    .with(context)
                    .load(list[position])
                    .centerInside()
                    .placeholder(R.drawable.estate_image_place_holder)
                    .into(holder.itemView.iv_estate_image)
                holder.itemView.fab_delete_image.visibility = View.GONE
            }else{
                Glide
                    .with(context)
                    .load(list[position])
                    .centerCrop()
                    .placeholder(R.drawable.estate_image_place_holder)
                    .into(holder.itemView.iv_estate_image)
                holder.itemView.fab_delete_image.visibility = View.VISIBLE

            }

            //holder.itemView.iv_estate_image.setImageURI(list[position])

            holder.itemView.setOnClickListener{
                if (onClickListener != null) {
                    onClickListener!!.onItemClick(position)
                }
            }

            holder.itemView.fab_delete_image.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onDeleteClick(position)
                }
            }
        }
    }


    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)

    }

    private fun estateImagePlaceHolderUri(): Uri {
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE +"://" +
                    context.resources.getResourcePackageName(R.drawable.estate_image_place_holder) + '/' +
                    context.resources.getResourceTypeName(R.drawable.estate_image_place_holder) + '/' +
                    context.resources.getResourceEntryName(R.drawable.estate_image_place_holder)
        )
    }

}

