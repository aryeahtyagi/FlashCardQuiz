package com.atria.software.flashcardquiz.ui.blockbuster

object helper {
    annotation class ViewFunctions()
    data class Report(
        val wrongQuestionsNo : Int = 0,
        val wrongQuestionIndexList : List<Int> = listOf(),
        val totalQuestionSize: Int = 0
    )
}