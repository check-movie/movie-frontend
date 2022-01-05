package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MyMoviesList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        var jsonStr: String = ""

        val getmovies = Thread(Runnable {
            try {
                val mURL = URL("https://citygame.ga/api/movies/show")
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
        getmovies.start()
        while(getmovies.isAlive){}

        var listview = findViewById<ListView>(R.id.listView)
        var list = mutableListOf<MoviesListModel>()

        val jsonArray = JSONArray(jsonStr)

        for(i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(MoviesListModel(jsonObject.optString("title"),"Premiera: "+ if(jsonObject.optString("release_date")=="null") "brak danych" else jsonObject.optString("release_date"),jsonObject.optString("check_movie_rating"),jsonObject.optString("poster"),jsonObject.optString("id").toInt()))
        }

        listview.setOnItemClickListener{ parent: AdapterView<*>, view: View, position:Int, id:Long ->
            var details: Intent = Intent(applicationContext, MyMovieDetails::class.java)
            details.putExtra("id", list.get(position).id)
            startActivity(details)
        }

        listview.adapter = MoviesListAdapter(this, R.layout.movierow, list)
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