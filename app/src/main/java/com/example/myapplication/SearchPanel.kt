package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_search_panel.*

class SearchPanel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_panel)
        titlesearch.setOnKeyListener { _, keyCode, event ->
            when {
                (((keyCode == KeyEvent.KEYCODE_ENTER)||(keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER)) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    searchFilm.performClick()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }
        searchFilm.setOnClickListener() {
            var search: Intent = Intent(applicationContext, MoviesSearchResult::class.java).apply {
                putExtra("searchdata", titlesearch.text.toString())
            }
            startActivity(search)
        }
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
            var mymovies: Intent = Intent(applicationContext, MyMoviesList::class.java);
            startActivity(mymovies);
            return true
        }
        else if(id==R.id.menubuttonmainpage){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}