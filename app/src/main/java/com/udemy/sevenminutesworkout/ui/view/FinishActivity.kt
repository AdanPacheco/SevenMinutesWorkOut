package com.udemy.sevenminutesworkout.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.udemy.sevenminutesworkout.WorkOutApp
import com.udemy.sevenminutesworkout.data.db.dao.HistoryDAO
import com.udemy.sevenminutesworkout.data.db.entities.HistoryEntity
import com.udemy.sevenminutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnFinish.setOnClickListener{
            onBackPressed()
        }

        val dao =(application as WorkOutApp).db.getHistoryDao()
        addRecordToHistory(dao)
    }

    private fun addRecordToHistory(historyDao:HistoryDAO){
        val c = Calendar.getInstance()
        val dateTime = c.time

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        val date = sdf.format(dateTime)


        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
        }
    }


}