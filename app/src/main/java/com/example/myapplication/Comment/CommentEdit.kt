package com.example.myapplication.Comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.User.UserToken
import kotlinx.android.synthetic.main.activity_comment_add.*
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class CommentEdit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_add)
        textView6.text="Edytuj komentarz"
        addc.text="Zatwierdź zmiany"
        comment.setText(intent.getStringExtra("opinion"))
        addc.setOnClickListener{
            var code = 1
            val ed = Thread(Runnable {
                try {
                    var reqParam = URLEncoder.encode("opinion", "UTF-8") + "=" + URLEncoder.encode(comment.text.toString(), "UTF-8")
                    val mURL = URL("https://citygame.ga/api/comment/"+intent.getIntExtra("id", 1)+"/update")

                    with(mURL.openConnection() as HttpURLConnection) {
                        setRequestProperty("Authorization", "Bearer $UserToken")
                        requestMethod = "POST"

                        val wr = OutputStreamWriter(getOutputStream());
                        wr.write(reqParam);
                        wr.flush();
                        code=responseCode;
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
            ed.start()
            while(ed.isAlive){}
            if(code==200){
                Toast.makeText(this, "Komentarz został edytowany prawidłowo.",
                    Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Edycja komentarza nie powiodła się. Spróbuj ponownie.",
                    Toast.LENGTH_LONG).show();
            }
            finish()
        }
    }
}