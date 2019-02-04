package com.perusudroid.galleryapp.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.perusudroid.galleryapp.R
import com.perusudroid.galleryapp.response.DateModel
import com.perusudroid.galleryapp.response.ImageModel
import java.lang.RuntimeException
import java.util.*

class GalleryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var galleryList: ArrayList<Any>

    fun setGalleryListData(galleryList2: ArrayList<Any>) {
        galleryList = galleryList2
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                return ImageViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.inflater_images, viewGroup, false))
            }
            2 -> {
                return DateViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.inflater_date, viewGroup, false))
            }
        }

        throw RuntimeException("Unknown View Type")
    }

    override fun getItemCount(): Int = galleryList.size

    override fun getItemViewType(position: Int): Int = if (galleryList[position] is ImageModel) 1 else 2

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is DateViewHolder -> {
                viewHolder.bindDates(galleryList[position] as DateModel)
            }
            is ImageViewHolder -> {
                viewHolder.bindPics(galleryList[position] as ImageModel)
            }
        }
    }

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var ivPic: ImageView = view.findViewById(R.id.ivPic)

        fun bindPics(imageModel: ImageModel) {

            Log.d("Gallery", "Pics ${imageModel.pic}")

            Glide.with(itemView.context)
                    .load(imageModel.pic)
                    .into(ivPic)
        }
    }


    class DateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDate: TextView = view.findViewById(R.id.tvDate)
        fun bindDates(dateModel: DateModel) {
            tvDate.text = dateModel.date
        }

    }


}