package com.atria.software.flashcardquiz.ui.blockbuster.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuizEntity::class], exportSchema = false, version = 1)
abstract class QuizDatabase: RoomDatabase() {

    private val database : QuizDatabase? = null
    abstract fun getQuizDao():QuizDao

    companion object{
        private var database : QuizDatabase? = null

        fun getQuizDatabase(context: Context):QuizDatabase{
            return if(database != null) {
                database as QuizDatabase
            }else{
                database = Room.databaseBuilder(context,QuizDatabase::class.java,"Quiz database")
                    .fallbackToDestructiveMigration()
                    .build()

                database as QuizDatabase
            }

        }
    }

}