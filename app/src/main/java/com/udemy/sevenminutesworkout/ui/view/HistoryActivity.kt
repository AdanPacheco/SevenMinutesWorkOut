package com.udemy.sevenminutesworkout.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.udemy.sevenminutesworkout.WorkOutApp
import com.udemy.sevenminutesworkout.data.db.dao.HistoryDAO
import com.udemy.sevenminutesworkout.databinding.ActivityHistoryBinding
import com.udemy.sevenminutesworkout.ui.viewmodel.historyAdapter.HistoryAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = (application as WorkOutApp).db.getHistoryDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDAO) {
        lifecycleScope.launch {
            historyDao.getAllHistoryEntries().collect { allCompletedDatesList ->
                if(allCompletedDatesList.isNotEmpty()) {
                    binding.rvDates.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val adapter = HistoryAdapter(allCompletedDatesList)
                    binding.rvDates.adapter = adapter
                }
            }
        }
    }
}