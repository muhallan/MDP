package com.teamvan.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    val number: Int,
    val question: String,
    val answer_index: Int,
    var is_answered: Int,
    var passed: Int,
    var selected_answer_index: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
