package com.example.fontis_fine_dine.models

import java.io.Serializable

data class Receipt(
    val id: String = "",
    val orderId: String = "",
    val notes: String = "",
    val receiptUrl: String = "",
    val uploadedBy: String = "",
    val timestamp: Long = System.currentTimeMillis()
) : Serializable

