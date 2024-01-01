package com.example.trippin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.trippin.R

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        change_page()
        pelogin()
        logout()
        changePass()
    }

    private fun pelogin(){
        val tv_nama:TextView = findViewById(R.id.profil_nama)
        val tv_email:TextView = findViewById(R.id.email_user)
        val tiket = getSharedPreferences("sesi_login", MODE_PRIVATE)
        val nama_pelogin = tiket.getString("nama", null).toString()
        tv_nama.text = nama_pelogin
        val email_pelogin = tiket.getString("email", null).toString()
        tv_email.text = email_pelogin
    }

    private fun change_page(){
        val btn_back:ImageView = findViewById(R.id.btn_back)
        btn_back.setOnClickListener {
            val back:Intent = Intent(this, MainActivity::class.java)
            startActivity(back)
            finish()
        }
    }

    private fun logout() {
        val btn_logout: ConstraintLayout = findViewById(R.id.logout_button)
        btn_logout.setOnClickListener {
            val tiket = getSharedPreferences("sesi_login", MODE_PRIVATE)
            val edit_tiket = tiket.edit()
            edit_tiket.clear()
            edit_tiket.commit()

            val backToLogin = Intent(this, SignInActivity::class.java)
            backToLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(backToLogin)
            finish()
        }
    }

    private fun changePass(){
        val btn_changePass:ConstraintLayout = findViewById(R.id.changePass_button)
        btn_changePass.setOnClickListener {
            val moveToChangePassPage:Intent = Intent(this, ChangePass::class.java)
            startActivity(moveToChangePassPage)
            Toast.makeText(this, "After Change Password You Must Re-login", Toast.LENGTH_LONG).show()
        }
    }
}