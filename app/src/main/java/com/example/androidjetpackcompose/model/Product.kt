package com.example.androidjetpackcompose.model

data class Product(
    val brand: String,
    val price: String,
    val image_link:  String,
    var description: String,
    val category: String?,
    val product_type: String,
    val name: String
)

