package com.example.e_commerceproductlistingapp

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Float,
    val category: String,
    val imageRes: Int, // Use R.drawable.image_name
    var inCart: Boolean = false
)