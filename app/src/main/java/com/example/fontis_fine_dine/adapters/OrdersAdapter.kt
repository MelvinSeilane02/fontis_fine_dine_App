package com.example.fontis_fine_dine.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fontis_fine_dine.R
import com.example.fontis_fine_dine.models.Order

class OrdersAdapter(
    private val orders: List<Order>,
    private val onClick: (Order) -> Unit
) : RecyclerView.Adapter<OrdersAdapter.OrderVH>() {

    inner class OrderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderId: TextView = itemView.findViewById(R.id.tvOrderId)
        val tvStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)
        val tvCustomer: TextView = itemView.findViewById(R.id.tvCustomer)
        val tvItems: TextView = itemView.findViewById(R.id.tvItemsPreview)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderVH(view)
    }

    override fun onBindViewHolder(holder: OrderVH, position: Int) {
        val order = orders[position]
        holder.tvOrderId.text = "Order #${order.orderId}"
        holder.tvStatus.text = order.status
        holder.tvCustomer.text = "By: ${order.customerName} | ${order.phone}"

        // Build items preview (e.g., "2x Kota, 1x Drink")
        val preview = order.items.groupingBy { it.name }.eachCount()
            .map { (name, qty) -> "${qty}x $name" }
            .joinToString(", ")
        holder.tvItems.text = preview

        holder.tvTotal.text = "R %.2f".format(order.total)

        holder.itemView.setOnClickListener { onClick(order) }
    }

    override fun getItemCount(): Int = orders.size
}