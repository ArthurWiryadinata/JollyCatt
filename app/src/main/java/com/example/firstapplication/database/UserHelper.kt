package com.example.firstapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.firstapplication.data.User

class UserHelper(context: Context) : SQLiteOpenHelper(context, "user.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreateUser = "CREATE TABLE IF NOT EXISTS User(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "phone TEXT," +
                "pass TEXT"+
                ")"
        db?.execSQL(queryCreateUser)
    }

    fun insertUser(user: User){
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", user.name)
            put("phone", user.phone)
            put("pass", user.pass)
        }
        db.insert("User", null, values)
        db.close()
    }

    fun getUsers() : ArrayList<User>{
        val users = ArrayList<User>()
        val db = readableDatabase
        val query = "SELECT * FROM User"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        if(cursor.count > 0){
            do{
                val user = User()
                user.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                user.name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                user.phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
                user.pass = cursor.getString(cursor.getColumnIndexOrThrow("pass"))
                users.add(user)
            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS User")
        onCreate(db)
    }

}