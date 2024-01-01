package com.example.trippin.activities
import android.app.ActivityOptions
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trippin.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.UUID


class SignInActivity : AppCompatActivity(), View.OnClickListener {
    private var tvWelcomeThere: TextView? = null
    private var textView: TextView? = null
    private var tvSignInToContinue: TextView? = null
    private var username: TextInputLayout? = null
    private var password: TextInputLayout? = null
    private var goButton: Button? = null
    private var signUpButton: Button? = null
    private var editUserName: TextInputEditText? = null
    private var editPassword: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initView()
        applyAnimations()
        checkUserSession()
    }

    private fun initView() {
        tvWelcomeThere = findViewById(R.id.welcome_message)
        textView = findViewById(R.id.textView)
        tvSignInToContinue = findViewById(R.id.welcome_message) // Ubah id sesuai dengan ID yang sesuai
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        goButton = findViewById(R.id.goButton)
        signUpButton = findViewById(R.id.signUpButton)
        editPassword = findViewById(R.id.editPassword)
        editUserName = findViewById(R.id.editUsername)
        goButton?.setOnClickListener(this)
        signUpButton?.setOnClickListener(this)
    }

    private fun applyAnimations() {
        // Animasi Zoom-In pada "Trippin"
        val zoomInAnimation = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom)
        textView?.startAnimation(zoomInAnimation)

        // Animasi Right-to-Center pada "Hello There, Welcome Back"
        val rightToCenterAnimation = AnimationUtils.loadAnimation(this, R.anim.right_to_center_signin)
        tvSignInToContinue?.startAnimation(rightToCenterAnimation)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.goButton -> loginValidation()
            R.id.signUpButton -> openSignUpPage()
            else -> {}
        }
    }

    private fun openSignUpPage() {
        val pairs: Array<Pair<View, String>?> = arrayOfNulls(6)
        pairs[0] = Pair(tvWelcomeThere!!, getString(R.string.welcome))
        pairs[1] = Pair(tvSignInToContinue!!, getString(R.string.signup_to_start_your_new_journey))
        pairs[2] = Pair(username!!, getString(R.string.username))
        pairs[3] = Pair(password!!, getString(R.string.password))
        pairs[4] = Pair(goButton!!, getString(R.string.sign_up))
        pairs[5] = Pair(signUpButton!!, getString(R.string.already_have_an_account_login))

        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(this@SignInActivity, *pairs.requireNoNulls())

        startActivity(intent, options.toBundle())
    }

    private fun loginValidation() {
        val inputUsername = editUserName?.text?.toString()
        val inputPassword = editPassword?.text?.toString()
        val konekDb: SQLiteDatabase = openOrCreateDatabase("trippin", MODE_PRIVATE, null)
        val queryPencocokan = konekDb.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?", arrayOf(inputUsername, inputPassword))

        if (queryPencocokan.moveToFirst()) {
            val id_user = queryPencocokan.getString(0)
            val email_user = queryPencocokan.getString(1)
            val nama_user = queryPencocokan.getString(2)
            val password_user = queryPencocokan.getString(3)

            val sesi = getSharedPreferences("sesi_login", MODE_PRIVATE)
            val cetak_tiket = sesi.edit()
            cetak_tiket.putString("id", id_user)
            cetak_tiket.putString("email", email_user)
            cetak_tiket.putString("nama", nama_user)
            cetak_tiket.putString("password", password_user)
            cetak_tiket.commit()
            saveUserSession(id_user)
            redirectToMainActivity()
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }

        queryPencocokan.close()
        konekDb.close()
    }

    private fun saveUserSession(id: String) {
        val konekDb: SQLiteDatabase = openOrCreateDatabase("trippin", MODE_PRIVATE, null)
        val tokenSession = generateSessionToken() // Fungsi untuk menghasilkan token sesi
        val query = "INSERT INTO UserSession (id_user, session_token) VALUES ('$id', '$tokenSession')"
        konekDb.execSQL(query)
        konekDb.close()
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkUserSession() {
        val sesi = getSharedPreferences("sesi_login", MODE_PRIVATE)
        val idUser = sesi.getString("id", null)

        if (idUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Fungsi untuk menghasilkan token sesi
    private fun generateSessionToken(): String {
        return UUID.randomUUID().toString()
    }
}