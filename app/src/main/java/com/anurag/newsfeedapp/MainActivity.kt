package com.anurag.newsfeedapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anurag.newsfeedapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataSet = generateData()
        binding.recyclerView.adapter = NewsListAdapter(dataSet)

    }

    private fun generateData(): ArrayList<String> {
        val dataset= ArrayList<String>()
        for (i in 0..200) {
            dataset.add("This is row no. ${i + 1}")
        }
        return dataset
    }
}