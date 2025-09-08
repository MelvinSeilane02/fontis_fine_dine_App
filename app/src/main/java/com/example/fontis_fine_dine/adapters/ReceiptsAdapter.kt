package com.example.fontis_fine_dine.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fontis_fine_dine.R
import com.example.fontis_fine_dine.models.Receipt
import com.squareup.picasso.Picasso

class ReceiptsAdapter(
    private var items: List<Receipt>,
    private val onClick: (Receipt) -> Unit
) : RecyclerView.Adapter<ReceiptsAdapter.ReceiptVH>() {

    inner class ReceiptVH(view: View) : RecyclerView.ViewHolder(view) {
        val ivReceipt: ImageView = view.findViewById(R.id.ivReceipt)
        val tvOrderId: TextView = view.findViewById(R.id.tvReceiptOrderId)
        val tvNotes: TextView = view.findViewById(R.id.tvReceiptNotes)
        val tvDate: TextView = view.findViewById(R.id.tvReceiptDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_receipt, parent, false)
        return ReceiptVH(v)
    }

    override fun onBindViewHolder(holder: ReceiptVH, position: Int) {
        val r = items[position]
        holder.tvOrderId.text = "Order #${r.orderId}"
        holder.tvNotes.text = r.notes
        holder.tvDate.text = android.text.format.DateFormat.format("dd-MM-yyyy", r.timestamp)
        if (r.receiptUrl.isNotEmpty()) {
            Picasso.get().load(r.receiptUrl).placeholder(R.drawable.receipt_icon_black).into(holder.ivReceipt)
        } else {
            holder.ivReceipt.setImageResource(R.drawable.receipt_icon_black)
        }
        holder.itemView.setOnClickListener { onClick(r) }
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<Receipt>) {
        (items as? MutableList)?.let {
            it.clear()
            it.addAll(newItems)
            notifyDataSetChanged()
        }
    }
}
