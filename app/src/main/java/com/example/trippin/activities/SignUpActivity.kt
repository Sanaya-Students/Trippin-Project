package com.example.trippin.activities

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.trippin.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private var editEmail: TextInputEditText? = null
    private var editUserName: TextInputEditText? = null
    private var editPassword: TextInputEditText? = null
    private var emailId: TextInputLayout? = null
    private var username: TextInputLayout? = null
    private var password: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initView()
    }

    private fun initView() {
        editEmail = findViewById(R.id.editEmailId)
        editUserName = findViewById(R.id.editUsername)
        editPassword = findViewById(R.id.editPassword)
        emailId = findViewById(R.id.email)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)

        findViewById<View>(R.id.alreadyHaveAccount).setOnClickListener(this)
        findViewById<View>(R.id.signUpButton).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.signUpButton -> {
                inputValidation()
            }
            R.id.alreadyHaveAccount -> onBackPressed()
        }
    }

    private fun inputValidation() {
        val input_email = editEmail?.text?.toString()
        val input_username = editUserName?.text?.toString()
        val input_password = editPassword?.text?.toString()

        val konek_db: SQLiteDatabase = openOrCreateDatabase("trippin", MODE_PRIVATE, null)
        val query_insert = konek_db.rawQuery("INSERT INTO user(email, username, password) VALUES ('$input_email', '$input_username', '$input_password')", null)
        query_insert.moveToNext()

        val jump_to_loginPage: Intent = Intent(this, SignInActivity::class.java)
        startActivity(jump_to_loginPage)
    }
}