package com.example.fontis_fine_dine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class item_adaptor(private val slideList: List<PopularOrders>) :
    RecyclerView.Adapter<item_adaptor.SlideViewHolder>() {

    // ViewHolder for each image slide
    class SlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleView: TextView = view.findViewById(R.id.titleTxt)
        val buyBtn: Button = view.findViewById(R.id.buyBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_selector, parent, false)
        return SlideViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val slide = slideList[position]
        holder.imageView.setImageResource(slide.ImgId) // Load the image resource
        holder.titleView.text = slide.FoodName
        holder.buyBtn.text = slide.Price
    }

    override fun getItemCount(): Int = slideList.size
}