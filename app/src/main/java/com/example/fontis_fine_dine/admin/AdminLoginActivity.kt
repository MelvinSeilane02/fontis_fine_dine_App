package com.example.fontis_fine_dine.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fontis_fine_dine.R
import com.example.fontis_fine_dine.R.id.btnLoginAdmin

class AdminLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(btnLoginAdmin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()

            // Basic validation
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ***** TEMPORARY HARD-CODED CHECK (DEV ONLY) *****
            if (username == "admin" && password == "AdminPass") {
                // proceed to admin dashboard
                val intent = Intent(this, AdminDashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}