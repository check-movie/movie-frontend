package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_result.*

class LoginResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_result)
        if(UserToken=="") {
            textView5.setText("Logowanie nie powiodło się. Sprawdź wprowadzone dane i spróbuj ponownie.")
            textView5.setTextColor(Color.RED)
        }
        else {
            textView5.setText("Zalogowano prawidłowo. Możesz teraz przejść do strony głównej.")
            textView5.setTextColor(Color.GREEN)
        }

        mainmenu.setOnClickListener {
            var strgl: Intent = Intent(applicationContext, SearchPanel::class.java)
            startActivity(strgl)
        }
    }
}