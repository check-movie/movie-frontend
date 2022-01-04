package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MoviesSearchResult : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        var jsonStr: String = ""

        val getmovies = Thread(Runnable {
            try {
                var link:String = "https://api.themoviedb.org/3/search/movie?api_key=9ed1ff5e176006301b0927dd2aefe12c&language=pl-PL&include_adult=true&query="+intent.getStringExtra("searchdata").toString();
                val mURL = URL(link)

                with(mURL.openConnection() as HttpURLConnection) {
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

        val jsonObject = JSONObject(jsonStr)
        val jsonArray = jsonObject.optJSONArray("results")

        for(i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(MoviesListModel(jsonObject.optString("title"),"Premiera: "+jsonObject.optString("release_date"),jsonObject.optString("vote_average"),"https://image.tmdb.org/t/p/original"+jsonObject.optString("poster_path"),jsonObject.optString("id").toInt()))
        }
        listview.adapter = MoviesListAdapter(this, R.layout.movierow, list)



        listview.setOnItemClickListener{ parent: AdapterView<*>, view: View, position:Int, id:Long ->
            var details: Intent = Intent(applicationContext, MovieDetails::class.java)
            details.putExtra("id", list.get(position).id)
            startActivity(details)
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