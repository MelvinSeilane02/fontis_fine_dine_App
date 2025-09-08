package com.example.fontis_fine_dine

import java.io.Serializable

data class FoodItem(
    val name: String,
    val price: String,
    val imgNme: Int,
    val catNme: String
) : Serializable
