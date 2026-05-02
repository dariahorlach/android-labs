package com.example.lr3d

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.formContainer, FormFragment())
                .commit()
        }
    }

    fun showResult(text: String, color: Int) {
        val resultFragment = ResultFragment.newInstance(text, color)

        supportFragmentManager.beginTransaction()
            .replace(R.id.resultContainer, resultFragment)
            .commit()
    }

    fun cancelAction() {
        val resultFragment = supportFragmentManager.findFragmentById(R.id.resultContainer)
        if (resultFragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(resultFragment)
                .commit()
        }

        val formFragment = supportFragmentManager.findFragmentById(R.id.formContainer) as? FormFragment
        formFragment?.clearForm()
    }

    fun navigateToViewData() {
        val intent = Intent(this, ViewDataActivity::class.java)
        startActivity(intent)
    }
}