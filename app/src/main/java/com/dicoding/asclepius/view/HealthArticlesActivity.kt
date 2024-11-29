package com.dicoding.asclepius.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.ActivityHealthArticlesBinding
import data.ApiConfig
import data.ResponseArtikel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HealthArticlesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHealthArticlesBinding
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Artikel"
            setDisplayHomeAsUpEnabled(true)
        }

        setupRecyclerView()
        fetchArticles()
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun fetchArticles() {
        binding.progressBar.visibility = View.VISIBLE
        ApiConfig.apiService.getHealthArticles().enqueue(object : Callback<ResponseArtikel> {
            override fun onResponse(call: Call<ResponseArtikel>, response: Response<ResponseArtikel>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    adapter.submitList(articles)
                } else {
                    Log.e("HealthArticlesActivity", "Failed to load articles")
                }
            }

            override fun onFailure(call: Call<ResponseArtikel>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e("HealthArticlesActivity", "Error: ${t.message}")
            }
        })
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