package com.example.fontis_fine_dine

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

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
}