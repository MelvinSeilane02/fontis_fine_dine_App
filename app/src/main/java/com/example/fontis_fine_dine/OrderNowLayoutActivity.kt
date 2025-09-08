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
import java.io.Serializable

class OrderNowLayoutActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var foodList: List<FoodItem>
    lateinit var checkoutlst: MutableList<FoodItem>
    lateinit var cart_btn: TextView

    data class CLickedItem(
        val name: String,
        val price: String,
        val imgNme: Int,
        val catNme: String
    ): Serializable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.order_now_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Declare
        cart_btn = findViewById(R.id.cart_btn)
        cart_btn.text = "0"

        checkoutlst = mutableListOf()

        recyclerView = findViewById(R.id.rvFoodList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample food data
        foodList = listOf(
            FoodItem("Extreme Russian Hour", "37.00", R.drawable.kota, "Kota Meal (Under R42)"),
            FoodItem("A Kasi's Bacon Kota", "37.00", R.drawable.kota2, "Kota Meal (Under R42)"),
            FoodItem("The Full Cheese Boi's Patty Express", "42.00", R.drawable.kota, "Premium Kota")
        )


        val adapter = FoodAdapter(foodList) { foodItem ->
            // This is the click event for each item
            Toast.makeText(this, "${foodItem.name} clicked!", Toast.LENGTH_SHORT).show()

            val item_clicked = FoodItem("${foodItem.name}",foodItem.price,foodItem.imgNme,"{$foodItem.catNme}")
            checkoutlst.add(item_clicked)

            cart_btn.text = "${checkoutlst.size}"

            // You could also start a new activity here
            // val intent = Intent(this, FoodDetailActivity::class.java)
            // intent.putExtra("food_name", foodItem.name)
            // intent.putExtra("food_price", foodItem.price)
            // startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    fun goto_cart_pg(view: View) {
        val intent = Intent(this, CartPageActivity::class.java)
        intent.putExtra("itemCount", checkoutlst.size)
        intent.putExtra("itemsClicked", ArrayList(checkoutlst))
        startActivity(intent)
    }

    fun go_home(view: View) {
        val homepage_intent = Intent(this, HomepageLayoutActivity::class.java)
        startActivity(homepage_intent)
    }
}

