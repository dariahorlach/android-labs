package com.example.lr5d

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var proximitySensor: Sensor? = null

    private lateinit var tvLightLevel: TextView
    private lateinit var tvProximity: TextView
    private lateinit var lockOverlay: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLightLevel = findViewById(R.id.tvLightLevel)
        tvProximity = findViewById(R.id.tvProximity)
        lockOverlay = findViewById(R.id.lockOverlay)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        proximitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_LIGHT -> handleLightSensor(event.values[0])
            Sensor.TYPE_PROXIMITY -> handleProximitySensor(event.values[0])
        }
    }

    private fun handleLightSensor(lux: Float) {
        tvLightLevel.text = "${lux.toInt()} lx"

        val maxLux = 1000f
        var brightnessValue = lux / maxLux

        brightnessValue = brightnessValue.coerceIn(0.1f, 1.0f)

        val layoutParams = window.attributes
        layoutParams.screenBrightness = brightnessValue
        window.attributes = layoutParams
    }

    private fun handleProximitySensor(distance: Float) {
        val maxRange = proximitySensor?.maximumRange ?: 5f

        if (distance < maxRange) {
            tvProximity.text = "Близько"
            tvProximity.setTextColor(Color.RED)
            lockOverlay.visibility = View.VISIBLE
        } else {
            tvProximity.text = "Далеко"
            tvProximity.setTextColor(Color.parseColor("#4CAF50"))
            lockOverlay.visibility = View.GONE
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}