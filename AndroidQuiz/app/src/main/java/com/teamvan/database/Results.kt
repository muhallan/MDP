package com.teamvan.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Results(
    var num_attempted: Int,
    var num_passed: Int,
    var num_timed_out: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
