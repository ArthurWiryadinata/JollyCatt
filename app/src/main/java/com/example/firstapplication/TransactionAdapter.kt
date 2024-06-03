package com.example.firstapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapplication.UserDatabase.transactionList

class TransactionAdapter(val itemList: MutableList<Transaction>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onDelBtnClick(transaction: Transaction)
        fun onUpdateBtnClick(transaction: Transaction)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nameTV = view.findViewById<TextView>(R.id.name2)
        val priceTV = view.findViewById<TextView>(R.id.price2)
        val descTV = view.findViewById<TextView>(R.id.desc2)
        val quantityTV = view.findViewById<TextView>(R.id.quantity2)
        val subTV = view.findViewById<TextView>(R.id.subtotal2)
        val updateBtn = view.findViewById<Button>(R.id.update)
        val deleteBtn = view.findViewById<Button>(R.id.delete)
    }

    fun updateTransaction(position: Int, transaction: Transaction) {
        itemList[position] = transaction
        notifyItemChanged(position)
        val newSubtotal = transaction.quantity.toDouble() * transaction.price.toDouble()
        transaction.sub = newSubtotal.toString()
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currItem = itemList[position]
        holder.nameTV.text = currItem.name
        holder.priceTV.text = currItem.price
        holder.descTV.text = currItem.desc
        holder.quantityTV.text = currItem.quantity
        holder.subTV.text = currItem.sub

        holder.updateBtn.setOnClickListener {
            itemClickListener.onUpdateBtnClick(currItem)
        }
        holder.deleteBtn.setOnClickListener {
            itemClickListener.onDelBtnClick(currItem)
        }
    }
}
