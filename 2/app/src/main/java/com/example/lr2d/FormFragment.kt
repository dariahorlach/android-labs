package com.example.lr2d

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

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

                (requireActivity() as MainActivity).showResult(inputText, selectedColor)
            }
        }
    }

    fun clearForm() {
        etInputText.text.clear()
        rgColors.clearCheck()
        etInputText.clearFocus()
    }
}