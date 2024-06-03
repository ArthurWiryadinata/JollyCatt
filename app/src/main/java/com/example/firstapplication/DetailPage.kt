package com.example.firstapplication

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.firstapplication.databinding.ActivityDetailPageBinding
import java.util.*

class DetailPage : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPageBinding
    private lateinit var nameET: TextView
    private lateinit var descET: TextView
    private lateinit var imageIV: ImageView
    private lateinit var backBTN: Button
    private lateinit var cartBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        nameET = binding.nameDetailPage
        descET = binding.descriptionDetailPage
        imageIV = binding.imageDetailPage
        backBTN = binding.backToHomePage
        cartBTN = binding.addToCart

        val catName = intent.getStringExtra("CatName")
        val catDesc = intent.getStringExtra("CatDescription")
        val catImage = intent.getStringExtra("CatImage")

        nameET.text = catName
        descET.text = catDesc

        // Load image using Glide
        Glide.with(this)
            .load(catImage)
            .into(imageIV)

        backBTN.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
        cartBTN.setOnClickListener {
            showCustomDialogBoc()
        }
    }

    private fun showCustomDialogBoc() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val quantity: EditText = dialog.findViewById(R.id.quantity)
        val btnYes: Button = dialog.findViewById(R.id.dialogYes)
        val btnNo: Button = dialog.findViewById(R.id.dialogNo)
        val catName = intent.getStringExtra("CatName")
        val catDesc = intent.getStringExtra("CatDescription")
        val catPrice = intent.getStringExtra("CatPrice")

        btnYes.setOnClickListener {
            val quantityText = quantity.text.toString()
            val name = catName ?: ""
            val desc = catDesc ?: ""
            val price = catPrice ?: ""

            if (quantityText.isNotEmpty() && name.isNotEmpty() && desc.isNotEmpty() && price.isNotEmpty()) {
                val quantityValue = quantityText.toIntOrNull()
                val priceValue = price.toDoubleOrNull()

                if (quantityValue != null && quantityValue > 0 && priceValue != null && priceValue > 0) {
                    val subTotal = quantityValue * priceValue
                    val newID = IDGenerator.generateRandomID()
                    val newBuyer = Transaction(quantityText, name, desc, price, subTotal.toString(), UserDatabase.currentID, newID)
                    UserDatabase.setterTransaction(newBuyer)
                    Toast.makeText(this@DetailPage, "Added to Cart", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                } else {
                    Toast.makeText(this@DetailPage, "Quantity and price must be valid numbers and greater than 0", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@DetailPage, "Please enter your Quantity", Toast.LENGTH_SHORT).show()
            }
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    object IDGenerator {
        private val generatedIDs = mutableSetOf<String>()

        fun generateRandomID(): String {
            val random = Random()
            var newID: String
            do {
                val timestamp = System.currentTimeMillis().toString()
                val randomID = StringBuilder()

                randomID.append(timestamp)

                repeat(6) {
                    randomID.append(random.nextInt(10))
                }
                newID = randomID.toString()
            } while (newID in generatedIDs)

            generatedIDs.add(newID)
            return newID
        }
    }
}
