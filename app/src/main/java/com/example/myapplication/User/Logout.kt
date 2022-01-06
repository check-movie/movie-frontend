package com.example.myapplication.User

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.Main.SearchPanel
import kotlinx.android.synthetic.main.activity_logout.*

class Logout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
        UserToken = ""
        val sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(SHAREDPREFSTOKEN, "")
        editor.apply()
        mainpage.setOnClickListener {
            var strgl: Intent = Intent(applicationContext, SearchPanel::class.java)
            startActivity(strgl)
            finish()
        }
    }
}