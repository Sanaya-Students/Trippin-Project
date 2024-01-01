package com.example.trippin.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trippin.Domain.Trends
import com.example.trippin.R
import com.example.trippin.adapter.TrendsAdapter

class MainActivity : AppCompatActivity() {
    private var adapterTrends: RecyclerView.Adapter<*>? = null
    private var recyclerViewTrends: RecyclerView? = null
    private var searchBox: EditText? = null
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        database = openOrCreateDatabase("trippin", MODE_PRIVATE, null)
        initRecyclerView()
        profileInit()
        exploreInit()
        pelogin()
        searchData()
        viewAll()
    }

    private fun pelogin() {
        val tvnama: TextView = findViewById(R.id.tvNama_user)
        val tiket = getSharedPreferences("sesi_login", MODE_PRIVATE)
        val namapelogin = tiket.getString("nama", null)

        if (namapelogin != null) {
            val greetingText = "Hi, $namapelogin"
            tvnama.text = greetingText
        } else {
            tvnama.text = "Hi, User"
        }
    }



    private fun profileInit() {
        val btn_profile = findViewById<LinearLayout>(R.id.btn_profile)
        btn_profile.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this@MainActivity,
                    Profile::class.java
                )
            )
        }
    }

    private fun viewAll(){
        val btnViewAll = findViewById<TextView>(R.id.viewAll)
        btnViewAll.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    Explore::class.java
                )
            )
        }

    }

    private fun exploreInit(){
        val btn_explore = findViewById<LinearLayout>(R.id.btn_explore)
        btn_explore.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this@MainActivity,
                    Explore::class.java
                )
            )
        }
    }

    @SuppressLint("Range")
    private fun initRecyclerView() {
        val items = ArrayList<Trends>()

        val cursor = database.rawQuery("SELECT * FROM search_data", null)

        var counter = 0
        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val subtitle = cursor.getString(cursor.getColumnIndex("subtitle"))
                val imageIndex = counter % 3 // Ambil indeks gambar dari array

                val imageArray = arrayOf(
                    R.drawable.bukit_paralayang,
                    R.drawable.tebing_breksi,
                    R.drawable.bukit_kelangon
                )

                val imageId = imageArray[imageIndex]
                items.add(Trends(title, subtitle, imageId))

                counter++
            } while (cursor.moveToNext())
        }

        cursor.close()

        recyclerViewTrends = findViewById(R.id.view1)
        recyclerViewTrends?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapterTrends = TrendsAdapter(items)
        recyclerViewTrends?.adapter = adapterTrends
    }

    @SuppressLint("Range")
    private fun searchData() {
        searchBox = findViewById(R.id.editSearchBox)
        val searchText = searchBox?.text.toString().trim()

        val cursor = database.rawQuery(
            "SELECT * FROM search_data WHERE title LIKE '%$searchText%' OR subtitle LIKE '%$searchText%'",
            null
        )

        val items = ArrayList<Trends>()

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val subtitle = cursor.getString(cursor.getColumnIndex("subtitle"))
                val imageIndex = getImageIndexFromArray(cursor)

                val imageArray = arrayOf(
                    R.drawable.bukit_paralayang,
                    R.drawable.tebing_breksi,
                    R.drawable.bukit_kelangon
                )

                val imageId = imageArray[imageIndex]
                items.add(Trends(title, subtitle, imageId))
            } while (cursor.moveToNext())
        } else {
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
        }

        cursor.close()

        recyclerViewTrends = findViewById(R.id.view1)
        recyclerViewTrends?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapterTrends = TrendsAdapter(items)
        recyclerViewTrends?.adapter = adapterTrends
    }

    private fun getImageIndexFromArray(cursor: Cursor): Int {
        val cursorIndex = cursor.position
        return cursorIndex % 3 // Kembalikan indeks gambar dari array
    }
}