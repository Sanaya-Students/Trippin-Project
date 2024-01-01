package com.example.trippin.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trippin.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ChangePass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_pass)

        displayPreviousPassword()
        insertNewPass()
    }

    @SuppressLint("Range")
    private fun displayPreviousPassword() {
        val prev_pass: TextInputLayout = findViewById(R.id.username) // Menggunakan ID yang sesuai dari layout XML
        val database: SQLiteDatabase = openOrCreateDatabase("trippin", MODE_PRIVATE, null)
        val query = "SELECT * FROM user WHERE id_user = ?"
        val tiket = getSharedPreferences("sesi_login", MODE_PRIVATE)
        val id_saatIni = tiket.getString("id", null).toString()

        // Menyediakan nilai ID pengguna saat ini sebagai parameter untuk query
        val selectionArgs = arrayOf(id_saatIni)
        val cursor: Cursor = database.rawQuery(query, selectionArgs)

        if (cursor.moveToFirst()) {
            val previousPassword = cursor.getString(cursor.getColumnIndex("password"))
            prev_pass.editText?.setText(previousPassword)
        }

        cursor.close()
        database.close()
    }

    private fun insertNewPass(){
        val btnChangePass: Button = findViewById(R.id.btn_changePass)
        val editNewPassword: TextInputEditText = findViewById(R.id.editNewPassword)
        val editRetypePass: TextInputEditText = findViewById(R.id.editRetypePass)

        btnChangePass.setOnClickListener {
            val newPassword = editNewPassword.text.toString().trim()
            val retypePassword = editRetypePass.text.toString().trim()

            if (newPassword.isEmpty() || retypePassword.isEmpty()) {
                Toast.makeText(this, "The password column cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != retypePassword) {
                Toast.makeText(this, "The new password does not match, please match again", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveNewPasswordToDatabase(newPassword)
            val moveToLoginPage:Intent = Intent(this, Profile::class.java)
            moveToLoginPage.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(moveToLoginPage)
            finish()
        }
    }

    private fun saveNewPasswordToDatabase(newPassword: String) {
        val tiket = getSharedPreferences("sesi_login", MODE_PRIVATE)
        val id_saatIni = tiket.getString("id", null).toString()

        val konek_db: SQLiteDatabase = openOrCreateDatabase("trippin", MODE_PRIVATE, null)

        val updateQuery = "UPDATE user SET password = ? WHERE id_user = ?"
        val selectionArgs = arrayOf(newPassword, id_saatIni)
        konek_db.execSQL(updateQuery, selectionArgs)
        konek_db.close()
        Toast.makeText(this, "Password successfully changed", Toast.LENGTH_SHORT).show()
    }
}
