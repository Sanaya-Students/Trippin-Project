package com.example.trippin.activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trippin.R
import com.example.trippin.adapter.ExploreAdapter

class Explore : AppCompatActivity() {
    private lateinit var lvHistory: ListView
    private lateinit var editTextSearch: EditText
    private lateinit var adapter: ExploreAdapter
    private lateinit var allHistory: MutableList<Pair<Int, String>>
    private lateinit var database: SQLiteDatabase
    private lateinit var sesi: SharedPreferences
    private lateinit var clear: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.explore)

        lvHistory = findViewById(R.id.lv_history)
        editTextSearch = findViewById(R.id.editSearchExplore)
        clear = findViewById(R.id.clear_all)
        sesi = getSharedPreferences("sesi_login", MODE_PRIVATE)

        database = openOrCreateDatabase("trippin", MODE_PRIVATE, null)

        allHistory = mutableListOf()

        adapter = ExploreAdapter(
            this,
            allHistory
        ) { id ->
            deleteHistoryById(id)
        }
        lvHistory.adapter = adapter

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = charSequence.toString().trim()
                if (searchText.isNotEmpty()) {
                    showFilteredHistory(searchText)
                } else {
                    loadHistoryFromDatabase()
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        editTextSearch.setOnEditorActionListener { _, _, _ ->
            val searchKeyword = editTextSearch.text.toString().trim()
            if (searchKeyword.isNotEmpty()) {
                insertKeywordToDatabase(searchKeyword)
                loadHistoryFromDatabase()
            }
            false
        }

//        editTextSearch.setOnClickListener {
//            showHistoryDialog()
//        }

        lvHistory.setOnItemClickListener { _, _, position, _ ->
            val selectedHistory = adapter.getItem(position)
            editTextSearch.setText(selectedHistory?.second)
        }

        clear.setOnClickListener {
            clearAllHistory()
        }

        loadHistoryFromDatabase()
    }

    private fun deleteHistoryById(id: Int) {
        val idUser = sesi.getString("id", "")

        val deletedRows = database.delete(
            "search_history",
            "id_history = ? AND id_user = ?",
            arrayOf(id.toString(), idUser)
        )

        if (deletedRows > 0) {
            loadHistoryFromDatabase()
        } else {
            Toast.makeText(this, "Failed to Delete History", Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertKeywordToDatabase(keyword: String) {
        val idUser = sesi.getString("id", "")
        val values = ContentValues().apply {
            put("history_data", keyword)
            put("id_user", idUser)
        }

        val cursor = database.rawQuery(
            "SELECT * FROM search_history WHERE history_data = ? AND id_user = ?",
            arrayOf(keyword, idUser)
        )

        if (cursor.count == 0) {
            database.insert("search_history", null, values)
        }

        cursor.close()
    }

    @SuppressLint("Range")
    private fun loadHistoryFromDatabase() {
        allHistory.clear()
        val idUser = sesi.getString("id", "")
        val cursor: Cursor = database.rawQuery(
            "SELECT * FROM search_history WHERE id_user = ?",
            arrayOf(idUser)
        )
        while (cursor.moveToNext()) {
            val historyID = cursor.getInt(cursor.getColumnIndex("id_history"))
            val historyData = cursor.getString(cursor.getColumnIndex("history_data"))
            allHistory.add(Pair(historyID, historyData))
        }
        cursor.close()
        adapter.notifyDataSetChanged()
    }

    private fun showFilteredHistory(searchText: String) {
        val filteredHistory = allHistory.filter { it.second.contains(searchText, ignoreCase = true) }
        adapter.clear()
        adapter.addAll(filteredHistory)
    }

//    private fun showHistoryDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("History Pencarian")
//        builder.setAdapter(adapter) { _, which ->
//            val selectedHistory = adapter.getItem(which)
//            editTextSearch.setText(selectedHistory?.second)
//        }
//        builder.show()
//    }

    private fun clearAllHistory() {
        val idUser = sesi.getString("id", "")
        database.delete("search_history", "id_user = ?", arrayOf(idUser))
        loadHistoryFromDatabase()
    }
}

