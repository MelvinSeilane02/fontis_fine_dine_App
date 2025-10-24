package com.example.fontis_fine_dine

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.fontis_fine_dine.adapters.item_adaptor
import com.example.fontis_fine_dine.admin.AdminActivity
import com.example.fontis_fine_dine.admin.AdminLoginActivity

class HomepageLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.homepage_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewPager: ViewPager2 = findViewById(R.id.popular_orders)

        val slides = listOf(
            PopularOrders(R.drawable.kota, "Kota (russian)", "R33"),
            PopularOrders(R.drawable.kota2, "Kota", "R40")
        )

        viewPager.adapter = item_adaptor(slides)
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

    fun goto_about_us(view: View) {
        val about_us_intent = Intent(this, AboutUsLayoutActivity::class.java)
        startActivity(about_us_intent)
    }
    fun goto_order_pg(view: View) {
        val order_page_intent = Intent(this, OrderNowLayoutActivity::class.java)
        startActivity(order_page_intent)
    }
    fun goto_tut_pg(view: View) {
        val tut_page_intent = Intent(this, HomepageLayoutActivity::class.java)
        startActivity(tut_page_intent)
    }

    fun show_more(view: View) {
        val moreOptionsButton = findViewById<ImageView>(R.id.optionBtn)

        moreOptionsButton.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.more_options_menu, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_settings -> {
                        Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.action_admin -> {
                        // open admin login or admin page
                        val intent = Intent(this, AdminLoginActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.action_logout -> {
                        Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }

            popup.show()
        }
    }
}