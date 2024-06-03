package com.example.firstapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.firstapplication.UserDatabase.userList
import com.example.firstapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toRegister: TextView
    private lateinit var UserET: EditText
    private lateinit var PasswordET: EditText
    private lateinit var LoginBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        toRegister = binding.loginpagebtn
        UserET = binding.UserNameLoginText
        PasswordET = binding.UserPasswordLoginText
        LoginBTN = binding.ContinueJourney

        toRegister.setOnClickListener{
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        LoginBTN.setOnClickListener{
            var enteredUsername = UserET.text.toString()
            var enteredPassword = PasswordET.text.toString()

            if(enteredUsername.isEmpty() && enteredPassword.isEmpty()){
                Toast.makeText(this, "Username and Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else{
                var userFound = false
                var index = 0;
                for(user in UserDatabase.userList){
                    if(enteredUsername == user.username){
                        userFound = true
                        if(user.password == enteredPassword){
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomePage::class.java)
                            UserDatabase.currentUsername = enteredUsername;
                            UserDatabase.currentPhone = userList[index].phone
                            UserDatabase.currentID = userList[index].userId
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                        }
                        break
                    }
                    index++;
                }
                if (!userFound) {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}