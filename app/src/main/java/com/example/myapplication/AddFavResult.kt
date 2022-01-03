package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_fav_result.*

class AddFavResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_fav_result)
        if(intent.getIntExtra("EXTRA_RESPONSE", 0)==200){
            textView5.setText("Prawidłowo dodano do ulubionych")
            textView5.setTextColor(Color.GREEN)
        }
        else{
            textView5.setText("Dodanie do ulubionych nie powiodło się. Spróbuj ponownie.")
            textView5.setTextColor(Color.RED)
        }
        mainmenu.setOnClickListener {
            var menugl: Intent = Intent(applicationContext, SearchPanel::class.java)
            startActivity(menugl)
        }
    }
}