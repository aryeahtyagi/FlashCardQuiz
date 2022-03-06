package com.atria.software.flashcardquiz.ui.blockbuster

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.atria.software.flashcardquiz.R
import com.atria.software.flashcardquiz.databinding.FragmentQuizBinding
import com.atria.software.flashcardquiz.ui.blockbuster.viewmodel.QuizFragmentViewModel
import com.atria.software.flashcardquiz.ui.blockbuster.viewmodel.QuizFragmentViewModelFactory


class QuizFragment : Fragment() {

    private var quizFragmentBinding: FragmentQuizBinding? = null
    private var quizFragmentViewModel: QuizFragmentViewModel? = null

    private val TAG = "QuizFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizFragmentBinding = FragmentQuizBinding.inflate(inflater, container, false)
        return quizFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quizFragmentBinding?.let {
            Log.i(TAG, "onViewCreated: $quizFragmentViewModel")
            quizFragmentViewModel =
                ViewModelProvider(
                    requireActivity(),
                    QuizFragmentViewModelFactory(
                        it,
                        viewLifecycleOwner
                    )
                ).get(QuizFragmentViewModel::class.java)

            quizFragmentViewModel?.setup(_binding = quizFragmentBinding){
                quizFragmentViewModel?.question_no?.observe(viewLifecycleOwner) {
                    quizFragmentViewModel?.onObserveTask(it,quizFragmentBinding)
                }
            }
            quizFragmentViewModel?.setProgress(it.progressBar)
        }
    }

}