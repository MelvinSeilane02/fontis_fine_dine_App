package com.example.fontis_fine_dine

import java.io.Serializable

data class GroupedFoodItem(
    val name: String,
    val price: String,
    val imgNme: String,
    val catNme: String,
    var quantity: Int
) : Serializable
