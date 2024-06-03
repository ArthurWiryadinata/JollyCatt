package com.example.firstapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NameAdapter(private val jsonList: ArrayList<json>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<NameAdapter.MyViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(item: json)
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.name1)
        val pictureIV: ImageView = itemView.findViewById(R.id.image1)
        val priceTV: TextView = itemView.findViewById(R.id.price1)
        val ltldesc: TextView = itemView.findViewById(R.id.desc1)
        val cardView: CardView = itemView.findViewById<CardView>(R.id.name_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.name_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return jsonList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem = jsonList[position]

        holder.nameTV.text = currItem.CatName
        holder.priceTV.text = currItem.CatPrice
        holder.ltldesc.text = currItem.CatDescription
        // Load image from URL using Glide
        Glide.with(holder.itemView.context)
            .load(currItem.CatImage)
            .into(holder.pictureIV)

        holder.cardView.setOnClickListener {
            itemClickListener.onItemClick(currItem)
        }
    }

}




// INI SEBELUM JSON
//class  NameAdapter(val itemList: List<Item>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<NameAdapter.ViewHolder>() {
//
//    interface OnItemClickListener{
//        fun onItemClick(item: Item)
//    }
//    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
//        val nameTV = view.findViewById<TextView>(R.id.name1)
//        val priceTV = view.findViewById<TextView>(R.id.price1)
//        val pictureIV = view.findViewById<ImageView>(R.id.image1)
//        val ltldesc = view.findViewById<TextView>(R.id.desc1)
//        val cardView = view.findViewById<CardView>(R.id.name_card)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.name_card, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val currItem = itemList[position]
//        holder.nameTV.text = currItem.name
//        holder.priceTV.text = currItem.price
//        holder.pictureIV.setImageResource(currItem.picture)
//        holder.ltldesc.text = currItem.descltl
//
//        holder.cardView.setOnClickListener {
//            itemClickListener.onItemClick(currItem)
//        }
//    }
//}