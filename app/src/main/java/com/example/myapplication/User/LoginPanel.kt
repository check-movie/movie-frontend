package com.example.myapplication.User

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.myapplication.Movie.MyMovie.MyMoviesList
import com.example.myapplication.R
import com.example.myapplication.Main.SearchPanel
import kotlinx.android.synthetic.main.activity_login_panel.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class LoginPanel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_panel)
        loginpassword.setOnKeyListener { _, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    logindo.performClick()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }
        loginmail.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(TextUtils.isEmpty(loginmail.text.toString()) || !Patterns.EMAIL_ADDRESS.matcher(loginmail.text.toString()).matches())
                    loginbladmail.setVisibility(View.VISIBLE)
                else
                    loginbladmail.setVisibility(View.INVISIBLE)
            }
        })

        loginpassword.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(loginpassword.text.toString().length<6)
                    loginbladhaslo.setVisibility(View.VISIBLE)
                else
                    loginbladhaslo.setVisibility(View.INVISIBLE)
            }
        })


        logindo.setOnClickListener{
            if(loginbladmail.visibility== View.VISIBLE || loginbladhaslo.visibility== View.VISIBLE) {
                loginblad.setText("Popraw powyższe błędy!")
                loginblad.setVisibility(View.VISIBLE)
                return@setOnClickListener
            }
            else
                loginblad.setVisibility(View.INVISIBLE)

            if(TextUtils.isEmpty(loginmail.text.toString()) || TextUtils.isEmpty(loginpassword.text.toString())) {
                loginblad.setText("Uzupełnij wszystkie pola w formularzu!")
                loginblad.setVisibility(View.VISIBLE)
                return@setOnClickListener
            }
            else
                loginblad.setVisibility(View.INVISIBLE)
            var code = 1
            val login = Thread(Runnable {
                try {
                    var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(loginmail.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(loginpassword.text.toString(), "UTF-8")
                    val mURL = URL("https://citygame.ga/api/auth/login")

                    with(mURL.openConnection() as HttpURLConnection) {
                        requestMethod = "POST"

                        val wr = OutputStreamWriter(getOutputStream());
                        wr.write(reqParam);
                        wr.flush();
                        code=responseCode

                        if(responseCode!=200){
                            UserToken = ""
                            return@with
                        }

                        BufferedReader(InputStreamReader(inputStream)).use {
                            val response = StringBuffer()

                            var inputLine = it.readLine()
                            while (inputLine != null) {
                                response.append(inputLine)
                                inputLine = it.readLine()
                            }
                            var token: String = JSONObject(response.toString()).get("access_token").toString()
                            UserToken = token
                            val sharedPreferences: SharedPreferences = getSharedPreferences(
                                SHARED_PREFS, Context.MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString(SHAREDPREFSTOKEN, token)
                            editor.apply()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })

            login.start()
            while(login.isAlive){}
            if(code==200){
                Toast.makeText(this, "Zalogowano prawidłowo.",
                    Toast.LENGTH_LONG).show();
                finish()
            }
            else {
                Toast.makeText(this, "Logowanie nie powiodło się. Spróbuj ponownie.",
                    Toast.LENGTH_LONG).show();
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(UserToken =="") {
            menuInflater.inflate(R.menu.main_menu, menu);
        }
        else{
            menuInflater.inflate(R.menu.main_menu_logged, menu);
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        if(id== R.id.menubuttonlogin){
            return true
        }
        else if(id== R.id.menubuttonregister){
            var register: Intent = Intent(applicationContext, RegisterPanel::class.java);
            startActivity(register);
            return true
        }
        else if(id== R.id.menubuttonmymovies){
            var mymovies: Intent = Intent(applicationContext, MyMoviesList::class.java);
            startActivity(mymovies);
            return true
        }
        else if(id== R.id.menubuttonmainpage){
            var search: Intent = Intent(applicationContext, SearchPanel::class.java);
            startActivity(search);
            return true
        }
        else if(id== R.id.menubuttonlogout){
            var wylog: Intent = Intent(applicationContext, Logout::class.java)
            startActivity(wylog)
            return true
        }
        else if(id== R.id.refresh){
            finish();
            startActivity(getIntent());
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}