package com.example.firstapplication
object UserDatabase {

    var currentID=""
    var currentUsername = ""
    var currentPhone = ""

    data class User(
        val username: String,
        val password: String,
        val phone: String,
        val userId: String
    )
    var userList = ArrayList<User>()
    var transactionList : MutableList<Transaction> = mutableListOf()
    var checkOutList = ArrayList<CheckOut>()

    fun getTransactions(): List<Transaction> {
        return transactionList
    }
    fun setterTransaction(transaction: Transaction){
        transactionList.add(transaction)
    }

}
