package com.example.fontis_fine_dine

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutUsLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.about_us_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun goto_order_pg(view: View) {
        val order_page_intent = Intent(this, OrderNowLayoutActivity::class.java)
        startActivity(order_page_intent)
    }

    fun go_home(view: View) {
        val homepage_intent = Intent(this, HomepageLayoutActivity::class.java)
        startActivity(homepage_intent)
    }
}