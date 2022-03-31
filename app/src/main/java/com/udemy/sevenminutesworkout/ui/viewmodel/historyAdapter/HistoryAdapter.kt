package com.udemy.sevenminutesworkout.ui.viewmodel.historyAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udemy.sevenminutesworkout.R
import com.udemy.sevenminutesworkout.data.db.entities.HistoryEntity

class HistoryAdapter(val historyList:List<HistoryEntity>) : RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(layoutInflater.inflate(R.layout.item_history_date,parent,false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.render(item,position+1)

    }

    override fun getItemCount(): Int {
       return historyList.size
    }


}