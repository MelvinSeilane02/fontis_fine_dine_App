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
import com.example.fontis_fine_dine.admin.AdminActivity
import com.example.fontis_fine_dine.admin.AdminLoginActivity

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

    fun goto_order_pg(view: View) {
        val order_page_intent = Intent(this, OrderNowLayoutActivity::class.java)
        startActivity(order_page_intent)
    }

    fun go_home(view: View) {
        val homepage_intent = Intent(this, HomepageLayoutActivity::class.java)
        startActivity(homepage_intent)
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
