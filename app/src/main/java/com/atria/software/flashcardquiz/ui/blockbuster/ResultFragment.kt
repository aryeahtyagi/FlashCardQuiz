package com.atria.software.flashcardquiz.ui.blockbuster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.atria.software.flashcardquiz.R
import com.atria.software.flashcardquiz.databinding.FragmentResultBinding
import com.atria.software.flashcardquiz.ui.blockbuster.helper.reportString

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val report = arguments?.getSerializable(reportString) as helper.Report?

        binding.subjectTextView.text = report?.subjectName
        binding.scoreTextView.text = report?.wrongQuestionsNo.toString()+"/"+report?.totalQuestionSize.toString()
        if(report?.wrongQuestionsNo != 0) {
            binding.wrongTextView.text = "Oops, you got ${report?.wrongQuestionsNo} wrong!"
        }else{
            binding.wrongTextView.text = "Wow, you got ${report?.wrongQuestionsNo} wrong!"
            binding.checkWrongAnswersButton.visibility = View.GONE
        }

        binding.finishQuizButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
        }

        binding.checkWrongAnswersButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(reportString,report)
            findNavController().navigate(R.id.action_resultFragment_to_quizWrongReportFragment,bundle)
        }

    }


}