package com.example.fontis_fine_dine.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fontis_fine_dine.R
import com.example.fontis_fine_dine.models.FoodItem

class FoodAdapter(
    private val foodList: MutableList<FoodItem>,
    private val onItemClick: (FoodItem) -> Unit, // Lambda to handle clicks
    private val onEdit: (FoodItem) -> Unit = {},
    private val onDelete: (FoodItem) -> Unit = {}
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    // ViewHolder holds the views in item layout
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView = itemView.findViewById(R.id.item_name)
        val foodPrice: TextView = itemView.findViewById(R.id.item_price)
        val foodCategory: TextView = itemView.findViewById(R.id.categoryNme)
        val foodImg: ImageView = itemView.findViewById(R.id.foodImg)

        // Admin action buttons
        /*val btnEdit: ImageButton? = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton? = itemView.findViewById(R.id.btnDelete)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lst_of_orders, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = foodList[position]
        holder.foodName.text = item.name
        holder.foodPrice.text = "R${item.price}"
        holder.foodCategory.text = item.catNme
        //holder.foodImg.setImageResource(item.imgNme)
        // Bind image (imgNme treated as drawable resource id)
        try {
            if (item.imgNme.isNullOrEmpty()) {
                holder.foodImg.setImageResource(item.imgNme.toIntOrNull() ?: 0)
            } else {
                holder.foodImg.setImageResource(R.drawable.lunch_dining_icon_black)
            }
        } catch (e: Exception) {
            holder.foodImg.setImageResource(R.drawable.lunch_dining_icon_black)
        }

        // Handle item click
        holder.itemView.setOnClickListener {
            onItemClick(item)

            // Edit / Delete actions (for admin)
            /*holder.btnEdit?.setOnClickListener { onEdit(item) }
            holder.btnDelete?.setOnClickListener { onDelete(item) }*/

            // Long press -> show popup menu with Edit / Delete options (admin actions)
            holder.itemView.setOnLongClickListener {
                showItemMenu(holder.itemView, item)
                true
            }

            // Long click triggers edit/delete for admin
            holder.itemView.setOnLongClickListener {
                onEdit(item)
                true
            }
        }

    }

    override fun getItemCount(): Int = foodList.size

     /** Replace list contents and refresh RecyclerView */
    fun update(newList: List<FoodItem>) {
        foodList.clear()
        foodList.addAll(newList)
        notifyDataSetChanged()
    }

    private fun showItemMenu(anchor: View, item: FoodItem) {
        val popup = PopupMenu(anchor.context, anchor)
        popup.menuInflater.inflate(R.menu.menu_food_item_actions, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_edit -> {
                    onEdit(item)
                    true
                }
                R.id.menu_delete -> {
                    onDelete(item)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}