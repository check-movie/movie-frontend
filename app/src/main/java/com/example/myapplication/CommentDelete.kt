package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_comment_delete.*
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class CommentDelete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_delete)
        nodelete.setOnClickListener{
            finish()
        }
        yesdelete.setOnClickListener{
            var code = 1
                val del = Thread(Runnable {
                    try {
                        val mURL = URL("https://citygame.ga/api/comment/"+intent.getIntExtra("id", 1)+"/destroy")

                        with(mURL.openConnection() as HttpURLConnection) {
                            setRequestProperty("Authorization", "Bearer $UserToken")
                            requestMethod = "DELETE"

                            val wr = OutputStreamWriter(getOutputStream());
                            wr.flush();
                            code=responseCode;
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                })
                del.start()
                while(del.isAlive){}
                if(code==200){
                    Toast.makeText(this, "Komentarz został usunięty prawidłowo.",
                        Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Usuwanie komentarza nie powiodło się. Spróbuj ponownie.",
                        Toast.LENGTH_LONG).show();
                }
                finish()
        }
    }
}