package com.teamvan.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ResultsDAO {
    @Insert
    suspend fun addResults(results: Results)

    @Query("SELECT * FROM results LIMIT 1")
    suspend fun getResults(): Results

    @Update
    suspend fun updateResults(results: Results)

    @Query("DELETE FROM results")
    suspend fun clearResults()
}
