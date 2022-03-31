package com.udemy.sevenminutesworkout.ui.viewmodel.exerciseAdapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.udemy.sevenminutesworkout.R
import com.udemy.sevenminutesworkout.databinding.ItemExerciseStatusBinding
import com.udemy.sevenminutesworkout.data.db.model.ExerciseModel

class ExerciseStatusViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemExerciseStatusBinding.bind(view)

    fun render(ex: ExerciseModel) {
        binding.tvItem.text = "${ex.getId()}"

        when {
            ex.getIsCompleted() -> {
                binding.tvItem.background = ContextCompat.getDrawable(itemView.context, R.drawable.item_circular_color_accent_background)
                binding.tvItem.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
            }

            ex.getIsSelected() -> {
                binding.tvItem.background = ContextCompat.getDrawable(itemView.context, R.drawable.item_circular_completed)
                binding.tvItem.setTextColor(ContextCompat.getColor(itemView.context,R.color.black))
            }
            else -> {
                binding.tvItem.background = ContextCompat.getDrawable(itemView.context, R.drawable.item_circular_color_gray_background)
                binding.tvItem.setTextColor(ContextCompat.getColor(itemView.context,R.color.black))
            }
        }
    }

}
