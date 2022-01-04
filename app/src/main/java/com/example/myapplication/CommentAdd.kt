package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_comment_add.*
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class CommentAdd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_add)
        val movieid: String = intent.getIntExtra("id", 1).toString()
        println("abc")
        println(movieid)
        comment.setOnKeyListener { _, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    addc.performClick()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }
        addc.setOnClickListener() {
            if(comment.text.toString().length in 4..250) {

                val add = Thread(Runnable {
                    try {
                        var reqParam = URLEncoder.encode("opinion", "UTF-8") + "=" + URLEncoder.encode(comment.text.toString(), "UTF-8")
                        val mURL = URL("https://citygame.ga/api/movie/"+intent.getIntExtra("id", 1)+"/comment/store")

                        with(mURL.openConnection() as HttpURLConnection) {
                            setRequestProperty("Authorization", "Bearer $UserToken")
                            requestMethod = "POST"

                            val wr = OutputStreamWriter(getOutputStream());
                            wr.write(reqParam);
                            wr.flush();
                            println(responseCode)
                            println(responseMessage)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                })
                add.start()
                while(add.isAlive){}
                finish()











            }
            else{
                Toast.makeText(this, "Komentarz musi zawierać od 4 do 250 znaków.",
                    Toast.LENGTH_LONG).show();
            }
        }
    }
}