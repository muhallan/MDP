package com.teamvan.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Answer(
    val question_id: Long,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
