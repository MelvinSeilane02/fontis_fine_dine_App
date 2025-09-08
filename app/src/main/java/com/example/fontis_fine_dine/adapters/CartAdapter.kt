package com.example.fontis_fine_dine.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fontis_fine_dine.GroupedFoodItem
import com.example.fontis_fine_dine.R

class CartAdapter(
    private val ItemsClicked: List<GroupedFoodItem>,
    private val listener: OnCartChangedListener
): RecyclerView.Adapter<CartAdapter.FoodViewHolder>() {

    // This is the modifiable copy that reflects quantity changes
    private val currentItems = ItemsClicked.map { it.copy() }.toMutableList()

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val foodName: TextView = itemView.findViewById(R.id.item_name)
        val foodPrice: TextView = itemView.findViewById(R.id.price)
        val foodImg: ImageView = itemView.findViewById(R.id.item_img)
        val minusBtn: Button = itemView.findViewById(R.id.minus_btn)
        val itemNum: TextView = itemView.findViewById(R.id.num_of_items)
        val addBtn: Button =itemView.findViewById(R.id.add_btn)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lst_of_checkout, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        val item = ItemsClicked[position]
        holder.foodName.text = item.name
        holder.foodPrice.text = /*item.price*/ getPrice(item.price, item.quantity)
        holder.foodImg.setImageResource(item.imgNme)
        holder.itemNum.text = item.quantity.toString()

        // SUBTRACT button logic
        holder.minusBtn.setOnClickListener {
            if (item.quantity > 0) {
                item.quantity--
                holder.itemNum.text = item.quantity.toString()
                holder.foodPrice.text = getPrice(item.price, item.quantity)
                updateCartCount()
            }
        }

        // ADD button logic
        holder.addBtn.setOnClickListener {
            item.quantity++
            holder.itemNum.text = item.quantity.toString()
            holder.foodPrice.text = getPrice(item.price, item.quantity)
            updateCartCount()
        }
    }


    override fun getItemCount(): Int {
        return ItemsClicked.size
    }



    private fun getPrice(price: String, quantity: Int): String {
        return (price.toDouble() * quantity).toString()
    }

    fun sub_btn_clicked(item: GroupedFoodItem, holder: FoodViewHolder) {
        if (item.quantity > 1) {
            item.quantity -= 1
            holder.itemNum.text = item.quantity.toString()

            val totalPrice = getPrice(item.price, item.quantity)
            holder.foodPrice.text = totalPrice
        }
        //onTotalUpdated(calculateTotal()) // update total
        updateCartCount()
    }

    fun add_btn_clicked(item: GroupedFoodItem, holder: FoodViewHolder) {
        item.quantity += 1
        holder.itemNum.text = item.quantity.toString()

        val totalPrice = getPrice(item.price, item.quantity)
        holder.foodPrice.text = totalPrice
        //onTotalUpdated(calculateTotal()) // update total
        updateCartCount()
    }

    fun calculateTotal(): Double {
        return ItemsClicked.sumOf {
            val price = it.price.toDoubleOrNull() ?: 0.0
            price * it.quantity
        }
    }

    // Function to calculate and call back to UI
    private fun updateCartCount() {
        val newCount = ItemsClicked.sumOf { it.quantity }
        val totalPrice = ItemsClicked.sumOf { it.price.toDouble() * it.quantity }
        listener.onCartCountUpdated(newCount, totalPrice)
    }

    interface OnCartChangedListener {
        fun onCartCountUpdated(newCount: Int, totalPrice: Double)
    }

    fun getUpdatedCartItems(): List<GroupedFoodItem> {
        return currentItems
    }

}