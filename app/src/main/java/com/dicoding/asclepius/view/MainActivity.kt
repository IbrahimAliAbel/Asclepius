package com.dicoding.asclepius.view


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications



class MainActivity : AppCompatActivity(), ImageClassifierHelper.ClassifierListener {

    private lateinit var binding: ActivityMainBinding
    private var currentImageUri: Uri? = null
    private val imageViewModel: ImageViewModel by viewModels()
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHealthArticles.setOnClickListener {
            startActivity(Intent(this, HealthArticlesActivity::class.java))
        }

        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = this
        )

        binding.galleryButton.setOnClickListener{startGallery()}

        binding.analyzeButton.setOnClickListener { analyzeImage() }

        imageViewModel.imageUri.observe(this) { uri ->
            uri?.let {
                currentImageUri = it
                showImage()
            }
        }
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            imageViewModel.setImageUri(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        if (currentImageUri != null) {

            imageClassifierHelper.classifyStaticImage(currentImageUri!!)
        } else {
            showToast("Silakan pilih gambar terlebih dahulu.")
        }
    }

    private fun moveToResult(label: String, confidence: Float) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("EXTRA_LABEL", label)
            putExtra("EXTRA_CONFIDENCE", confidence)
            putExtra("EXTRA_IMAGE_URI", currentImageUri.toString())
        }
        startActivity(intent)
    }


    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
        results?.firstOrNull()?.categories?.firstOrNull()?.let { category ->
            val label = category.label
            val confidence = category.score
            moveToResult(label, confidence)
        } ?: showToast("Gagal mengenali gambar.")
    }

    override fun onError(error: String) {
        showToast(error)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}