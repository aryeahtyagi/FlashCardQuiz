package com.atria.software.flashcardquiz.ui.blockbuster.room

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuizEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int=0,
    var subject : String="",
    var topic : String="",
    var questions : Int=0,
) {
}