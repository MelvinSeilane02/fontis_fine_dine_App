package com.example.fontis_fine_dine

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fontis_fine_dine.models.FoodItem
import com.google.firebase.firestore.FirebaseFirestore

class AddEditDishActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var editingItemId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_dish)

        val etName = findViewById<EditText>(R.id.etName)
        val etPrice = findViewById<EditText>(R.id.etPrice)
        val etCategory = findViewById<EditText>(R.id.etCategory)
        val etImageUrl = findViewById<EditText>(R.id.etImageUrl)
        val btnSave = findViewById<Button>(R.id.btnSaveDish)

        // If an existing FoodItem was passed, populate fields
        val food = intent.getSerializableExtra("food") as? FoodItem
        food?.let {
            editingItemId = it.id
            etName.setText(it.name)
            etPrice.setText(it.price.toString())
            etCategory.setText(it.catNme)
            etImageUrl.setText(it.imgNme)
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val price = etPrice.text.toString().trim()
            val cat = etCategory.text.toString().trim()
            val img = etImageUrl.text.toString().trim()

            if (name.isEmpty() || price.isEmpty() || cat.isEmpty()) {
                Toast.makeText(this, "Please fill name, price and category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = hashMapOf(
                "name" to name,
                "price" to price,
                "imgNme" to img,
                "catNme" to cat
            )

            if (editingItemId == null) {
                // Add new
                db.collection("menu")
                    .add(data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Dish added", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                // Update existing
                db.collection("menu").document(editingItemId!!)
                    .set(data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Dish updated", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
