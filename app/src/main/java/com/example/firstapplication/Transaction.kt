package com.example.firstapplication

data class Transaction(
    val quantity: String,
    val name: String,
    val desc: String,
    val price: String,
    var sub: String,
    val userID: String,
    var newID: String,
)

