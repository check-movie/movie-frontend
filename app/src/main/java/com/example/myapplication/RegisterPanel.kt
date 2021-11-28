package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class RegisterPanel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_panel)
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
            return true
        }
        else if(id==R.id.menubuttonmymovies){
            var mymovies: Intent = Intent(applicationContext, MyMoviesList::class.java);
            startActivity(mymovies);
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}