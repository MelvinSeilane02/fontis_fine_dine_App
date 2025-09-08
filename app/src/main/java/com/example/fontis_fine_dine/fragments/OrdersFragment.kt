package com.example.fontis_fine_dine.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fontis_fine_dine.OrderDetailActivity
import com.example.fontis_fine_dine.R
import com.example.fontis_fine_dine.adapters.OrdersAdapter
import com.example.fontis_fine_dine.models.FoodItem
import com.example.fontis_fine_dine.models.Order
import com.google.firebase.FirebaseApp

class OrdersFragment : Fragment() {

    private lateinit var rvOrders: RecyclerView
    private lateinit var tvNoOrders: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        // Initialize Firebase
        FirebaseApp.initializeApp(requireContext())

        rvOrders = view.findViewById(R.id.rvOrders)
        tvNoOrders = view.findViewById(R.id.tvOrdersTitle) // placeholder title reuse

        // Prepare mock orders (replace with Firestore fetch later)
        val mockOrders = createMockOrders()

        // Setup RecyclerView
        rvOrders.layoutManager = LinearLayoutManager(requireContext())
        val adapter = OrdersAdapter(mockOrders) { order ->
            // Open OrderDetailActivity and pass the Order object
            val intent = Intent(requireContext(), OrderDetailActivity::class.java)
            intent.putExtra("order", order)
            startActivity(intent)
        }
        rvOrders.adapter = adapter

        return view
    }

    private fun createMockOrders(): List<Order> {
        val food1 = FoodItem(name = "Kota", price = 30.00, imgNme = 0, catNme = "Main")
        val food2 = FoodItem(name = "Fries", price = 15.00, imgNme = 0, catNme = "Sides")
        val food3 = FoodItem(name = "Coke", price = 10.00, imgNme = 0, catNme = "Drinks")

        val o1 = Order(
            orderId = "1001",
            customerName = "John Doe",
            phone = "+27820000000",
            items = listOf(food1, food2),
            total = 45.0,
            status = "Pending",
            paid = false
        )

        val o2 = Order(
            orderId = "1002",
            customerName = "Thabo M",
            phone = "+27821111111",
            items = listOf(food1, food3),
            total = 40.0,
            status = "Processing",
            paid = false
        )

        val o3 = Order(
            orderId = "1003",
            customerName = "Nandi K",
            phone = "+27823333333",
            items = listOf(food2, food3),
            total = 25.0,
            status = "Completed",
            paid = true
        )

        return listOf(o1, o2, o3)
    }
}