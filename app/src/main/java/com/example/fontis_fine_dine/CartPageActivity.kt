package com.example.fontis_fine_dine

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartPageActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var selectedFood: List<FoodItem>
    private lateinit var updatedItems: List<GroupedFoodItem>
    private var totPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.cart_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val cartCount = intent.getIntExtra("itemCount", 0)
        findViewById<TextView>(R.id.cart_btn).text = cartCount.toString()


        // Receive and cast the Serializable extra
        val receivedList = intent.getSerializableExtra("itemsClicked") as? ArrayList<FoodItem>

        if (receivedList != null) {
            selectedFood = receivedList
            Toast.makeText(this, "Items received: ${selectedFood.size}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No items received", Toast.LENGTH_SHORT).show()
            selectedFood = emptyList()
        }

        val kotaPicked : List<GroupedFoodItem> = groupItems(selectedFood)


        recyclerView = findViewById(R.id.rvFoodList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = CartAdapter(kotaPicked, object : CartAdapter.OnCartChangedListener {
            override fun onCartCountUpdated(newCount: Int, totalPrice: Double) {
                findViewById<TextView>(R.id.cart_btn).text = newCount.toString()

                // Also update your sum TextView
                findViewById<TextView>(R.id.sum).text = String.format("%.2f", totalPrice)
                totPrice = totalPrice
            }
        })
        recyclerView.adapter = adapter
        updatedItems = adapter.getUpdatedCartItems()

        // 👇 Immediately show initial sum
        val initialTotal = kotaPicked.sumOf {
            (it.price.toDoubleOrNull() ?: 0.0) * it.quantity
        }
        findViewById<TextView>(R.id.sum).text = String.format("%.2f", initialTotal)

    }

    fun groupItems(items: List<FoodItem>): List<GroupedFoodItem> {
        return items.groupingBy { it }
            .eachCount()
            .map { (foodItem, count) ->
                GroupedFoodItem(
                    name = foodItem.name,
                    price = foodItem.price,
                    imgNme = foodItem.imgNme,
                    catNme = foodItem.catNme,
                    quantity = count
                )
            }
    }

    fun goto_order_pg(view: View) {
        val order_page_intent = Intent(this, OrderNowLayoutActivity::class.java)
        startActivity(order_page_intent)
    }

    fun goto_checkout(view: View) {
        val intent = Intent(this, CheckoutPageActivity::class.java)
        intent.putExtra("total_price", totPrice)
        intent.putExtra("groupedItems", ArrayList(updatedItems)) // kotaPicked is List<GroupedFoodItem>
        startActivity(intent)
    }
}