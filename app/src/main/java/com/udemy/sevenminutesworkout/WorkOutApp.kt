package com.udemy.sevenminutesworkout

import android.app.Application
import com.udemy.sevenminutesworkout.data.db.HistoryDatabase

class WorkOutApp : Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}