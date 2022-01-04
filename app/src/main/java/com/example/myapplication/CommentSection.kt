package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_comment_section.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CommentSection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_section)

        var jsonStr: String = ""

        val getcomments = Thread(Runnable {
            try {
                val mURL = URL("https://citygame.ga/api/movies/"+intent.getIntExtra("id", 1).toString()+"/show")
                with(mURL.openConnection() as HttpURLConnection) {

                    setRequestProperty("Authorization", "Bearer $UserToken")
                    requestMethod = "GET"

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        it.close()
                        jsonStr = response.toString()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        getcomments.start()
        while(getcomments.isAlive){}

        var listview = findViewById<ListView>(R.id.listView)
        var list = mutableListOf<CommentModel>()

        var jsonArrayx = JSONArray(jsonStr)
        var jsonObjectx = jsonArrayx.getJSONObject(0)
        var jsonArray = jsonObjectx.optJSONArray("comments")

        for(i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(CommentModel(jsonObject.optString("opinion"),jsonObject.optString("author"),jsonObject.optString("author_avatar"),jsonObject.optString("created_at"),jsonObject.optString("id").toInt()))
        }
        listview.adapter = CommentAdapter(this, R.layout.commentrow, list)
        addcomment.setOnClickListener {
            var addcomm: Intent = Intent(applicationContext, CommentAdd::class.java)
            addcomm.putExtra("id", intent.getIntExtra("id", 1))
            startActivity(addcomm)

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(UserToken=="") {
            menuInflater.inflate(R.menu.main_menu, menu);
        }
        else{
            menuInflater.inflate(R.menu.main_menu_logged, menu);
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        if(id==R.id.menubuttonlogin){
            var login: Intent = Intent(applicationContext, LoginPanel::class.java);
            startActivity(login);
            return true
        }
        else if(id==R.id.menubuttonregister){
            var register: Intent = Intent(applicationContext, RegisterPanel::class.java);
            startActivity(register);
            return true
        }
        else if(id==R.id.menubuttonmymovies){
            var mymovies: Intent = Intent(applicationContext, MyMoviesList::class.java);
            startActivity(mymovies);
            return true
        }
        else if(id==R.id.menubuttonmainpage){
            var search: Intent = Intent(applicationContext, SearchPanel::class.java);
            startActivity(search);
            return true
        }
        else if(id==R.id.menubuttonlogout){
            var wylog: Intent = Intent(applicationContext, Logout::class.java)
            startActivity(wylog)
            return true
        }
        else if(id==R.id.refresh){
            finish();
            startActivity(getIntent());
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}