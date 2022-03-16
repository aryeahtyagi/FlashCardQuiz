package com.atria.software.flashcardquiz.ui.blockbuster.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface QuizDao {

    @Query("Select * from quizentity")
    fun getTopics(): LiveData<List<QuizEntity>>

    @Query("Select * from quizentity where subject =:subject ")
    fun getForSubject(subject : String) : LiveData<List<QuizEntity>>


}