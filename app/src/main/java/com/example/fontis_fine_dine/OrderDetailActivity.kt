package com.example.fontis_fine_dine

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fontis_fine_dine.models.Order
//import com.example.fontis_fine_dine.models.Order

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val order = intent.getParcelableExtra<Order>("order")
        val tvOrderId = findViewById<TextView>(R.id.tvDetailOrderId)
        val tvCustomer = findViewById<TextView>(R.id.tvDetailCustomer)
        val tvItems = findViewById<TextView>(R.id.tvDetailItems)
        val tvTotal = findViewById<TextView>(R.id.tvDetailTotal)
        val btnMarkPaid = findViewById<Button>(R.id.btnMarkPaid)
        val btnUpdateStatus = findViewById<Button>(R.id.btnUpdateStatus)

        order?.let {
            tvOrderId.text = "Order #${it.orderId}"
            tvCustomer.text = "${it.customerName} | ${it.phone}"
            tvItems.text = it.items.joinToString("\n") { f -> "${f.name} - R ${f.price}" }
            tvTotal.text = "R %.2f".format(it.total)
        }

        btnMarkPaid.setOnClickListener {
            // TODO: update Firestore order paid flag
        }

        btnUpdateStatus.setOnClickListener {
            // TODO: open a dialog to pick new status and update Firestore
        }
    }
}