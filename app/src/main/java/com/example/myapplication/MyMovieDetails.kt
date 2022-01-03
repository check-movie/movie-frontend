package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_movie_details.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MyMovieDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_movie_details)

        var jsonStr: String = ""

        val getmovie = Thread(Runnable {
            try {
                //TODO connect to correct endpoint
                val mURL = URL("https://api.themoviedb.org/3/movie/"+intent.getIntExtra("id", 557).toString()+"?api_key=9ed1ff5e176006301b0927dd2aefe12c&language=pl-PL")
                //
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
        getmovie.start()
        while(getmovie.isAlive){}

        var jsonObject = JSONObject(jsonStr)
        Picasso.get().load(jsonObject.optString("poster")).into(zdjecie)
        tytul.text = jsonObject.optString("title")
        origtytul.text = jsonObject.optString("original_title")
        opis.text = jsonObject.optString("overview")
        homepage.text = jsonObject.optString("homepage")
        datawyd.text = jsonObject.optString("release_date")
        srocen.text = jsonObject.optString("vote_average")
        ilocen.text = jsonObject.optString("vote_count")
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_logged, menu);
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
        return super.onOptionsItemSelected(item)
    }
}