package com.udemy.sevenminutesworkout.data.db.dao

import androidx.room.*
import com.udemy.sevenminutesworkout.data.db.entities.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDAO {

    @Insert
    suspend fun insert(he: HistoryEntity)

    @Query("SELECT * FROM `history-table`")
    fun getAllHistoryEntries():Flow<List<HistoryEntity>>
}