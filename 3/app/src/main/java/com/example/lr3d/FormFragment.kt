package com.example.lr3d

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.FileOutputStream

class FormFragment : Fragment() {

    private lateinit var etInputText: EditText
    private lateinit var rgColors: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rgColors = view.findViewById(R.id.rgColors)
        etInputText = view.findViewById(R.id.etInputText)
        val btnOk = view.findViewById<Button>(R.id.btnOk)
        val btnOpen = view.findViewById<Button>(R.id.btnOpen)

        btnOk.setOnClickListener {
            val inputText = etInputText.text.toString().trim()
            val selectedColorId = rgColors.checkedRadioButtonId

            if (inputText.isEmpty() || selectedColorId == -1) {
                Toast.makeText(requireContext(), "Будь ласка, введіть текст та оберіть колір!", Toast.LENGTH_SHORT).show()
            } else {
                val selectedColor = when (selectedColorId) {
                    R.id.rbRed -> Color.RED
                    R.id.rbGreen -> Color.GREEN
                    R.id.rbBlue -> Color.BLUE
                    else -> Color.BLACK
                }

                val colorName = view.findViewById<RadioButton>(selectedColorId).text.toString()
                val dataToSave = "Текст: $inputText | Колір: $colorName\n"
                saveToFile(dataToSave)

                (requireActivity() as MainActivity).showResult(inputText, selectedColor)
            }
        }

        btnOpen.setOnClickListener {
            (requireActivity() as MainActivity).navigateToViewData()
        }
    }

    private fun saveToFile(data: String) {
        try {
            val outputStream: FileOutputStream = requireContext().openFileOutput("saved_data.txt", Context.MODE_APPEND)
            outputStream.write(data.toByteArray())
            outputStream.close()
            Toast.makeText(requireContext(), "Дані успішно збережено у файл!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Помилка збереження", Toast.LENGTH_SHORT).show()
        }
    }

    fun clearForm() {
        etInputText.text.clear()
        rgColors.clearCheck()
        etInputText.clearFocus()
    }
}