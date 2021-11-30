package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MyMoviesList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        //TODO laczenie z api, pobieranie filmow do listy

        var listview = findViewById<ListView>(R.id.listView)
        var list = mutableListOf<MoviesListModel>()

        //tymczasowe dane do testow, w przyszlosci wypelniane danymi z api
        list.add(MoviesListModel("Film Pierwszy","1111","2","Reżyser Pierwszy","https://w7.pngwing.com/pngs/130/1021/png-transparent-movie-logo-movie-logo-film-tape-cinema.png",0))
        list.add(MoviesListModel("Film Drugi","2222","2.5","Reżyser Drugi","https://w7.pngwing.com/pngs/130/1021/png-transparent-movie-logo-movie-logo-film-tape-cinema.png",1))
        list.add(MoviesListModel("Film trzeci","3333","3","Reżyser Trzeci","https://w7.pngwing.com/pngs/130/1021/png-transparent-movie-logo-movie-logo-film-tape-cinema.png",2))
        list.add(MoviesListModel("Film Czwarty","4444","3.5","Reżyser Czwarty","https://w7.pngwing.com/pngs/130/1021/png-transparent-movie-logo-movie-logo-film-tape-cinema.png",3))
        list.add(MoviesListModel("Film Piąty","5555","4","Reżyser Piąty","https://w7.pngwing.com/pngs/130/1021/png-transparent-movie-logo-movie-logo-film-tape-cinema.png",4))
        list.add(MoviesListModel("Film Szósty","6666","4.5","Reżyser Szósty","https://w7.pngwing.com/pngs/130/1021/png-transparent-movie-logo-movie-logo-film-tape-cinema.png",5))
        list.add(MoviesListModel("Film Siódmy","7777","5","Reżyser Siódmy","https://w7.pngwing.com/pngs/130/1021/png-transparent-movie-logo-movie-logo-film-tape-cinema.png",6))

        listview.adapter = MoviesListAdapter(this, R.layout.movierow, list)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu);
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
            var search: Intent = Intent(applicationContext, MainActivity::class.java);
            startActivity(search);
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}