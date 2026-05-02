package com.example.lr1d

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rgColors = findViewById<RadioGroup>(R.id.rgColors)
        val etInputText = findViewById<EditText>(R.id.etInputText)
        val btnOk = findViewById<Button>(R.id.btnOk)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnOk.setOnClickListener {
            val inputText = etInputText.text.toString().trim()
            val selectedColorId = rgColors.checkedRadioButtonId

            if (inputText.isEmpty() || selectedColorId == -1) {
                Toast.makeText(
                    this,
                    "Будь ласка, введіть текст та оберіть колір!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val selectedColor = when (selectedColorId) {
                    R.id.rbRed -> Color.RED
                    R.id.rbGreen -> Color.GREEN
                    R.id.rbBlue -> Color.BLUE
                    else -> Color.BLACK
                }

                tvResult.text = inputText
                tvResult.setTextColor(selectedColor)
            }
        }

        btnCancel.setOnClickListener {
            etInputText.text.clear()
            rgColors.clearCheck()
            tvResult.text = ""
        }
    }
}