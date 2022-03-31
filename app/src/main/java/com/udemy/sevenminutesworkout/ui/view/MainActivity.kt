package com.udemy.sevenminutesworkout.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udemy.sevenminutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListeners()
    }

    private fun initClickListeners() {
        binding.frmStartButton.setOnClickListener {
            startActivity(Intent(this, ExerciseActivity::class.java))
        }

        binding.frmCalculateBMI.setOnClickListener {
            startActivity(Intent(this, BMIActivity::class.java))
        }

        binding.frmHistory.setOnClickListener{
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}