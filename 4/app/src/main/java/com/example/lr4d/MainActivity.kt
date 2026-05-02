package com.example.lr4d

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    private var exoPlayer: ExoPlayer? = null
    private lateinit var mediaPlayerView: PlayerView

    private var activeUri: String? = null
    private var savedPosition: Long = 0L
    private var isPlayerReady: Boolean = true

    private val audioPicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { startPlayback(it.toString()) }
    }

    private val videoPicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { startPlayback(it.toString()) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayerView = findViewById(R.id.mediaPlayerView)
        val etNetworkUrl = findViewById<EditText>(R.id.etNetworkUrl)
        val btnLoadUrl = findViewById<Button>(R.id.btnLoadUrl)
        val btnSelectAudio = findViewById<Button>(R.id.btnSelectAudio)
        val btnSelectVideo = findViewById<Button>(R.id.btnSelectVideo)

        if (savedInstanceState != null) {
            activeUri = savedInstanceState.getString("URI")
            savedPosition = savedInstanceState.getLong("POS", 0L)
            isPlayerReady = savedInstanceState.getBoolean("STATE", true)
        }

        setupExoPlayer()

        activeUri?.let {
            restorePlayback(it)
        }

        btnLoadUrl.setOnClickListener {
            val urlString = etNetworkUrl.text.toString().trim()
            if (urlString.isNotEmpty()) {
                startPlayback(urlString)
            } else {
                Toast.makeText(this, "Поле URL порожнє!", Toast.LENGTH_SHORT).show()
            }
        }

        btnSelectAudio.setOnClickListener { audioPicker.launch("audio/*") }
        btnSelectVideo.setOnClickListener { videoPicker.launch("video/*") }
    }

    private fun setupExoPlayer() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(this).build()
            mediaPlayerView.player = exoPlayer
        }
    }

    private fun startPlayback(uriString: String) {
        activeUri = uriString
        setupExoPlayer()

        val item = MediaItem.fromUri(uriString.toUri())
        exoPlayer?.setMediaItem(item)
        exoPlayer?.playWhenReady = true
        exoPlayer?.prepare()
    }

    private fun restorePlayback(uriString: String) {
        setupExoPlayer()

        val item = MediaItem.fromUri(uriString.toUri())
        exoPlayer?.setMediaItem(item)
        exoPlayer?.seekTo(savedPosition)
        exoPlayer?.playWhenReady = isPlayerReady
        exoPlayer?.prepare()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("URI", activeUri)
        outState.putLong("POS", exoPlayer?.currentPosition ?: 0L)
        outState.putBoolean("STATE", exoPlayer?.playWhenReady ?: true)
    }

    override fun onStop() {
        super.onStop()
        exoPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }
}