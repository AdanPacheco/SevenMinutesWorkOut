package com.udemy.sevenminutesworkout.ui.viewmodel.exerciseAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udemy.sevenminutesworkout.R
import com.udemy.sevenminutesworkout.data.db.model.ExerciseModel

class ExerciseStatusAdapter(private val listExercise: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseStatusViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseStatusViewHolder(layoutInflater.inflate(R.layout.item_exercise_status, parent, false))
    }

    override fun onBindViewHolder(holder: ExerciseStatusViewHolder, position: Int) {
        val ex = listExercise[position]
        holder.render(ex)
    }

    override fun getItemCount(): Int {
        return listExercise.size
    }
}