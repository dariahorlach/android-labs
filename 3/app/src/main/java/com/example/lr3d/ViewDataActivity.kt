package com.example.lr3d

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class ViewDataActivity : AppCompatActivity() {

    private val fileName = "saved_data.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data)

        val tvFileData = findViewById<TextView>(R.id.tvFileData)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnClear = findViewById<Button>(R.id.btnClear)

        loadData(tvFileData)

        btnBack.setOnClickListener { finish() }

        btnClear.setOnClickListener {
            deleteFile(fileName)
            tvFileData.text = "Сховище пусте"
            Toast.makeText(this, "Дані видалено", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData(textView: TextView) {
        try {
            val fileInputStream = openFileInput(fileName)
            val reader = BufferedReader(InputStreamReader(fileInputStream))
            val sb = StringBuilder()
            var line: String? = reader.readLine()

            if (line == null) {
                textView.text = "Сховище пусте"
                return
            }

            while (line != null) {
                sb.append(line).append("\n")
                line = reader.readLine()
            }
            textView.text = sb.toString()
            fileInputStream.close()
        } catch (e: Exception) {
            textView.text = "Сховище пусте"
        }
    }
}