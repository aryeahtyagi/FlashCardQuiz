package com.atria.software.flashcardquiz.ui.blockbuster.viewmodel

import android.app.Fragment
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atria.software.flashcardquiz.databinding.FragmentQuizBinding
import com.atria.software.flashcardquiz.ui.blockbuster.customviews.RoundCornerProgressBar
import com.atria.software.flashcardquiz.ui.blockbuster.helper
import java.util.*
import kotlin.collections.HashMap

class QuizFragmentViewModel(
    private var binding: FragmentQuizBinding? = null,
    private val viewLifecycleOwner: LifecycleOwner,
    private val topic: String = "",
    private val questionsPairList : List<Pair<String, String>> ,
) : ViewModel() {

    companion object {
        private const val TAG = "QuizFragmentViewModel"
    }

    private val duration = 10_000L;// 30 sec
    private lateinit var questions: List<Pair<String, String>>
    val question_no = MutableLiveData<Int>(1);

    val wrongQuestionsPairList = mutableListOf<Pair<String, String>>()

    private val wrongQuestionsIndexList = mutableListOf<Int>()
    private var wrongQuestionsCount = 0

    @helper.ViewFunctions
    var resultCallback = MutableLiveData<helper.Report>(null)
        get() = field

    private var progressBar: RoundCornerProgressBar? = null

    var end_progress = 0f

    @helper.ViewFunctions
    fun setProgress(bar: RoundCornerProgressBar?) {
        if (progressBar != null) {
            bar?.stopSmoothProgress()
        }
        progressBar = bar
        progressBar?.setProgress(end_progress)
        progressBar?.setSmoothProgress(1f, duration = duration) {
            val current_value = question_no.value ?: 0
            question_no.postValue(current_value.plus(1))
            progressBar?.setProgress(0f)
            end_progress = 0f
            setProgress(progressBar)
        }
    }


    fun onObserveTask(it: Int, _binding: FragmentQuizBinding?) {
        Log.i(TAG, "setup: ")
        if (questions.size < it) {
            // here all questions are completed
            val report =
                helper.Report(wrongQuestionsCount, wrongQuestionsIndexList, questions.size, topic,wrongQuestionsPairList)
            Log.i(TAG, report.toString())
            resultCallback.postValue(report)
        } else {
            _binding?.questionTextView?.text = questions.elementAt(it.minus(1)).first
            _binding?.answerTextView?.text = questions.elementAt(it.minus(1)).second
            _binding?.questionNumber?.text = it.toString() + "/" + questions.size.toString()
        }
    }


    fun setup(_binding: FragmentQuizBinding?, onViewObserver: () -> Unit) {
        if (progressBar != null) {
            end_progress = progressBar?.getProgress() ?: 0f
        }
        progressBar = _binding?.progressBar
        questions = getQuestionsListFromDatabase() {
            setTapToReveal(_binding)
            onViewObserver()
        }
    }

    @helper.ViewFunctions
    private fun setTapToReveal(_binding: FragmentQuizBinding?) {
        val answerRequestVisibilityBucket = listOf(
            _binding?.answerTextView,
            _binding?.choiceLayout
        )
        _binding?.cardView?.setOnClickListener {
            progressBar?.stopSmoothProgress()
            answerRequestVisibilityBucket.forEach { it?.visibility = View.VISIBLE }
        }

        _binding?.rightAnswerButton?.setOnClickListener {
            val current_value = question_no.value ?: 0
            hideViews(_binding)
            end_progress = 0f
            setProgress(progressBar)
            question_no.postValue(current_value.plus(1))
        }

        _binding?.wrongAnswerButton?.setOnClickListener {
            wrongQuestionsCount += 1
            question_no.value?.let { it1 ->
                wrongQuestionsIndexList.add(it1)
                wrongQuestionsPairList.add(questions[it1-1])

            }
            val current_value = question_no.value ?: 0
            hideViews(_binding)
            question_no.postValue(current_value.plus(1))
        }


    }

    @helper.ViewFunctions
    private fun hideViews(_binding: FragmentQuizBinding?) {
        val answerRequestVisibilityBucket = listOf(
            _binding?.answerTextView,
            _binding?.choiceLayout
        )
        answerRequestVisibilityBucket.forEach { it?.visibility = View.GONE }
    }

    @helper.ViewFunctions
    fun View.animatedReveal() {
        if (this is TextView) {
            // probably the answer is the view
        } else {

        }
    }


    private fun getQuestionsListFromDatabase(onFetched: () -> Unit): List<Pair<String, String>> {
        return questionsPairList
            .also {
            onFetched()
        }
    }


}

class QuizFragmentViewModelFactory(
    private val binding: FragmentQuizBinding,
    private val viewLifecycleOwner: LifecycleOwner,
    private val topic: String,
    private val questionsPairList : List<Pair<String, String>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuizFragmentViewModel(binding, viewLifecycleOwner, topic,questionsPairList) as T
    }

}