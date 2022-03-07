package com.atria.software.flashcardquiz.ui.blockbuster

import android.os.Parcelable
import java.io.Serializable

object helper {
    annotation class ViewFunctions()
    data class Report(
        val wrongQuestionsNo : Int = 0,
        val wrongQuestionIndexList : List<Int> = listOf(),
        val totalQuestionSize: Int = 0,
        val subjectName :String = "",
        val wrongAnswersPair : List<Pair<String,String>> = listOf()
    ) : Serializable

    val reportString = "report"
}