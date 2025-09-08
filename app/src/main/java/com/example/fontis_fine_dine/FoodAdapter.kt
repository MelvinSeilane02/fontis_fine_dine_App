package com.example.fontis_fine_dine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(
    private val foodList: List<FoodItem>,
    private val onItemClick: (FoodItem) -> Unit // Lambda to handle clicks
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    // ViewHolder holds the views in item layout
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView = itemView.findViewById(R.id.item_name)
        val foodPrice: TextView = itemView.findViewById(R.id.item_price)
        val foodCategory: TextView = itemView.findViewById(R.id.categoryNme)
        val foodImg: ImageView = itemView.findViewById(R.id.foodImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lst_of_orders, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = foodList[position]
        holder.foodName.text = item.name
        holder.foodPrice.text = item.price
        holder.foodImg.setImageResource(item.imgNme)
        holder.foodCategory.text = item.catNme

        // Handle item click
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = foodList.size
}
