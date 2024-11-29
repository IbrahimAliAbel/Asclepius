package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.dicoding.asclepius.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Hasil Analisis"
        supportActionBar?.apply {
            title = "Hasil Analisis"
            setDisplayHomeAsUpEnabled(true)
        }

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.

        val imageUri = intent.getStringExtra("EXTRA_IMAGE_URI")?.let { Uri.parse(it) }
        val label = intent.getStringExtra("EXTRA_LABEL") ?: "Tidak ada label"
        val confidence = intent.getFloatExtra("EXTRA_CONFIDENCE", 0f)


        imageUri?.let {
            binding.resultImage.setImageURI(it)
        }


        binding.predictionText.text = label
        binding.confidenceText.text = "${(confidence * 100).toInt()}%"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}


