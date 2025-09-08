package com.example.fontis_fine_dine

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fontis_fine_dine.adapters.FoodAdapter
import com.example.fontis_fine_dine.admin.AdminActivity
import com.example.fontis_fine_dine.models.FoodItem
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
            FoodItem("01","Extreme Russian Hour", 37.00, R.drawable.kota, "Kota Meal (Under R42)"),
            FoodItem("02","A Kasi's Bacon Kota", 37.00, R.drawable.kota2, "Kota Meal (Under R42)"),
            FoodItem(
                "03",
                "The Full Cheese Boi's Patty Express",
                42.00,
                R.drawable.kota,
                "Premium Kota"
            )
        )


        // make sure `foodList` is MutableList<FoodItem>
        val adapter = FoodAdapter(foodList.toMutableList(),

            onItemClick ={ foodItem ->
                // This is the click event for each item
                Toast.makeText(this, "${foodItem.name} clicked!", Toast.LENGTH_SHORT).show()

                // Create item to add to checkout (fields: id, name, price, imgNme, catNme)
                val itemClicked = FoodItem(
                    id = "",
                    name = foodItem.name,
                    price = foodItem.price,
                    imgNme = foodItem.imgNme,
                    catNme = foodItem.catNme
                )

                checkoutlst.add(itemClicked)
                cart_btn.text = "${checkoutlst.size.toString()}"



                // Optional: open detail activity
                // val intent = Intent(this, FoodDetailActivity::class.java)
                // intent.putExtra("food", foodItem)
                // startActivity(intent)
            }
        )

        recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu from XML
        menuInflater.inflate(R.menu.more_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle settings
                true
            }
            R.id.action_admin -> {
                // Open Admin Page Activity
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_logout -> {
                // Handle logout
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

