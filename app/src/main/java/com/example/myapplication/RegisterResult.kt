package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_result.*

class RegisterResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_result)
        if(intent.getIntExtra("EXTRA_RESPONSE", 0)==200){
            textView5.setText("Zarejestrowano prawidłowo. Możesz teraz się zalogować.")
            textView5.setTextColor(Color.GREEN)
        }
        else{
            textView5.setText("Rejestracja nie powiodła się. Sprawdź wprowadzone dane i spróbuj ponownie.")
            textView5.setTextColor(Color.RED)
        }
        mainmenu.setOnClickListener {
            var menugl: Intent = Intent(applicationContext, SearchPanel::class.java)
            startActivity(menugl)
        }
    }
}