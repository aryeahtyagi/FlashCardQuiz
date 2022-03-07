package com.atria.software.flashcardquiz.ui.blockbuster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.atria.software.flashcardquiz.R
import com.atria.software.flashcardquiz.databinding.FragmentQuizBinding
import com.atria.software.flashcardquiz.databinding.FragmentQuizWrongReportBinding
import com.atria.software.flashcardquiz.ui.blockbuster.helper.reportString

class QuizWrongReportFragment : Fragment() {

    private lateinit var binding : FragmentQuizWrongReportBinding
    private var report : helper.Report? = null
    private var index = MutableLiveData<Int>(0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuizWrongReportBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(report == null){
            report = arguments?.getSerializable(reportString) as helper.Report?
        }

        index.observe(viewLifecycleOwner){
            if (it< report?.wrongAnswersPair?.size?:0) {
                binding.questionTextView.text = report?.wrongAnswersPair?.get(it)?.first
                binding.answerTextView.text = report?.wrongAnswersPair?.get(it)?.second
                binding.questionNumber.text = it.plus(1).toString() + "/" + report?.wrongQuestionsNo
            }else{
                findNavController().navigate(R.id.action_quizWrongReportFragment_to_subjectFragment)
            }
        }

        binding.nextButton.setOnClickListener {
            val currentValue = index.value?:-1
            index.postValue(currentValue.plus(1))
        }

    }

}