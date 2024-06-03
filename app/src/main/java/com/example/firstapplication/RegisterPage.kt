package com.example.firstapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.firstapplication.UserDatabase.User
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firstapplication.databinding.ActivityRegisterPageBinding

class RegisterPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var UserET : EditText
    private lateinit var PasswordET : EditText
    private lateinit var PhoneET : EditText
    private lateinit var RegisBTN : Button
    private lateinit var ToLoginPage : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        UserET = binding.UserNameRegisterText
        PasswordET = binding.UserPasswordRegisterText
        PhoneET = binding.UserPhoneRegisterText
        RegisBTN = binding.BeginYourJourney
        ToLoginPage = binding.LoginAccRegisterText

        ToLoginPage.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        RegisBTN.setOnClickListener{
            var username = UserET.text.toString()
            var password = PasswordET.text.toString()
            var phone = PhoneET.text.toString()
            fun generateUserId(): String {
                val formattedCounter = String.format("%03d", UserDatabase.userList.size)
                return "UID-$formattedCounter"
            }
            if (username.isEmpty() || username.length < 8 ){
                Toast.makeText(this, "User Name Cannot Be Empty and must more than 8 characters", Toast.LENGTH_SHORT).show()
            }
            else if (!username.matches("[a-zA]+|[0-9]+".toRegex())) {
                Toast.makeText(this, "Username can only contain letters or numbers", Toast.LENGTH_SHORT).show();
            }
            else if(UserDatabase.userList.any { it.username == username }) {
                Toast.makeText(this, "Username is already taken, please choose a different one", Toast.LENGTH_SHORT).show()
            }
            else if (password.isEmpty() || password.length < 5) {
                Toast.makeText(this, "Password must be more than 5 characters and cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if (!password.matches(".*[A-Z].*".toRegex()) && !password.matches(".*[a-z].*".toRegex())) {
                Toast.makeText(this, "Password must have at least 1 letter", Toast.LENGTH_SHORT).show()
            }
            else if (!password.matches(".*[0-9].*".toRegex())) {
                Toast.makeText(this, "Password must contain at least 1 number", Toast.LENGTH_SHORT).show()
            }
            else if (!password.matches(".*[~`(){}!@#$%^&].*".toRegex())) {
                Toast.makeText(this, "Password must contain at least 1 symbol", Toast.LENGTH_SHORT).show()
            }
            else if(phone.isEmpty()){
                Toast.makeText(this,"Phone Number cannot be empty and Begin with '0' or '+'", Toast.LENGTH_SHORT).show()
            }
            else if(phone.length < 8 || phone.length > 20){
                Toast.makeText(this, "Phone Number Must Be Between 8 - 20 Characters", Toast.LENGTH_SHORT).show()
            }
            else if (!phone.matches("^[0+][0-9]+$".toRegex())) {
                Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
            }
            else{

                var builder= AlertDialog.Builder(this)
                builder.setTitle("Last Confirmation")
                builder.setMessage("Are You Sure ?")

                builder.setPositiveButton("Yes") {dialog, which ->
                    var id = generateUserId()
                    val newUser = User(username, password, phone, id)
                    UserDatabase.userList.add(newUser)
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity:: class.java)
                    startActivity(intent)
                }
                builder.setNegativeButton("No") {dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }

        }


    }
}

