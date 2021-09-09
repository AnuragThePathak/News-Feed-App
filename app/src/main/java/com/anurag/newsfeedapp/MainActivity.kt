package com.anurag.newsfeedapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anurag.newsfeedapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ItemTapped {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataSet = generateData()
        binding.recyclerView.adapter = NewsListAdapter(dataSet, this)

    }

    private fun generateData(): ArrayList<String> {
        val dataset = ArrayList<String>()
        for (i in 0..200) {
            dataset.add("This is row no. ${i + 1}")
        }
        return dataset
    }

    override fun onRecyclerTapped(item: String) {
        Toast.makeText(this, "$item is tapped.", Toast.LENGTH_SHORT).show()
    }
}