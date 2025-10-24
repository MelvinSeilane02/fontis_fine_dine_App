package com.example.fontis_fine_dine.models

import android.R
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

// Food item you gave (with minor formatting)
data class FoodItem(
    var id: String = "",
    var name: String = "",
    var price: String  = "0.0",
    var imgNme: String = "",
    var catNme: String = ""
) : Serializable

// Example order data model
@Parcelize
data class Order(
    val orderId: String = "",
    val customerName: String = "",
    val phone: String = "",
    val items: List<FoodItem> = emptyList(),
    val total: Double = 0.0,
    val status: String = "Pending", // e.g., Pending, Accepted, Processing, Completed
    val paid: Boolean = false,
    val timestamp: Long = System.currentTimeMillis(),
    val receiptUrl: String? = null // optional link to receipt image (Firebase Storage)
) : Parcelable

// Admin user model if you store admins in DB
@Parcelize
data class AdminUser(
    val uid: String = "",     // unique id
    val username: String = "",
    val role: String = "admin", // future roles possible
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable