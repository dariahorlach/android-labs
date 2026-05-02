package com.example.lr3d

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvResult = view.findViewById<TextView>(R.id.tvResult)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        val text = arguments?.getString("TEXT") ?: ""
        val color = arguments?.getInt("COLOR") ?: android.graphics.Color.BLACK

        tvResult.text = text
        tvResult.setTextColor(color)

        btnCancel.setOnClickListener {
            (requireActivity() as MainActivity).cancelAction()
        }
    }

    companion object {
        fun newInstance(text: String, color: Int): ResultFragment {
            val args = Bundle()
            args.putString("TEXT", text)
            args.putInt("COLOR", color)

            val fragment = ResultFragment()
            fragment.arguments = args
            return fragment
        }
    }
}