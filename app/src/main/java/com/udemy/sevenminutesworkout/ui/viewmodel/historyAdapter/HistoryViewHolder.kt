package com.udemy.sevenminutesworkout.ui.viewmodel.historyAdapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.udemy.sevenminutesworkout.R
import com.udemy.sevenminutesworkout.data.db.entities.HistoryEntity
import com.udemy.sevenminutesworkout.databinding.ItemHistoryDateBinding

class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemHistoryDateBinding.bind(view)

    fun render(item: HistoryEntity, position: Int) {
        binding.tvPosition.text = "$position"
        binding.tvDate.text = item.date

        if (position % 2 == 0) {
            binding.llRowDate.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.lightGray))
        }
    }
}