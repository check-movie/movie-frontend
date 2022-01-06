package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_panel.*
import java.io.DataOutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class RegisterPanel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_panel)
        editTextTextPassword2.setOnKeyListener { _, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    registerdo.performClick()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        editTextTextPersonName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editTextTextPersonName.text.toString().length<1)
                    regerrimie.setVisibility(View.VISIBLE)
                else
                    regerrimie.setVisibility(View.INVISIBLE)
            }
        })

        editTextTextEmailAddress.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(TextUtils.isEmpty(editTextTextEmailAddress.text.toString()) || !Patterns.EMAIL_ADDRESS.matcher(editTextTextEmailAddress.text.toString()).matches())
                    regerrmail.setVisibility(View.VISIBLE)
                else
                    regerrmail.setVisibility(View.INVISIBLE)
            }
        })

        editTextTextPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editTextTextPassword.text.toString().length<6)
                    regerrpass.setVisibility(View.VISIBLE)
                else
                    regerrpass.setVisibility(View.INVISIBLE)
            }
        })

        editTextTextPassword2.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editTextTextPassword2.text.toString()!=editTextTextPassword.text.toString())
                    regerrpasscon.setVisibility(View.VISIBLE)
                else
                    regerrpasscon.setVisibility(View.INVISIBLE)
            }
        })

        registerdo.setOnClickListener{
            if(regerrimie.visibility==View.VISIBLE || regerrmail.visibility==View.VISIBLE || regerrpass.visibility==View.VISIBLE || regerrpasscon.visibility==View.VISIBLE) {
                regerr.setText("Popraw powyższe błędy!")
                regerr.setVisibility(View.VISIBLE)
                return@setOnClickListener
            }
            else
                regerr.setVisibility(View.INVISIBLE)

            if(TextUtils.isEmpty(editTextTextPersonName.text.toString()) || TextUtils.isEmpty(editTextTextEmailAddress.text.toString()) || TextUtils.isEmpty(editTextTextPassword.text.toString()) || TextUtils.isEmpty(editTextTextPassword2.text.toString())) {
                regerr.setText("Uzupełnij wszystkie pola w formularzu!")
                regerr.setVisibility(View.VISIBLE)
                return@setOnClickListener
            }
            else
                regerr.setVisibility(View.INVISIBLE)
            var code = 1
            val register = Thread(Runnable {
                try {
                    var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(editTextTextEmailAddress.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(editTextTextPassword.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("password_confirmation", "UTF-8") + "=" + URLEncoder.encode(editTextTextPassword2.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(editTextTextPersonName.text.toString(), "UTF-8")
                    val mURL = URL("https://citygame.ga/api/auth/register")

                    with(mURL.openConnection() as HttpURLConnection) {
                        requestMethod = "POST"

                        val wr = DataOutputStream(getOutputStream());
                        var buffer:ByteArray = reqParam.toByteArray()
                        wr.write(buffer);
                        wr.flush();
                        code=responseCode
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
            register.start()
            while(register.isAlive){}
            if(code==200){
                Toast.makeText(this, "Zarejestrowano prawidłowo.",
                    Toast.LENGTH_LONG).show();
                finish()
            }
            else {
                Toast.makeText(this, "Rejestracja nie powiodła się. Spróbuj ponownie.",
                    Toast.LENGTH_LONG).show();
            }
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