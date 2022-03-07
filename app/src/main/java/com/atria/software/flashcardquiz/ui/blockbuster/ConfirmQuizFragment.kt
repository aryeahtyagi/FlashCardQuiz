package com.atria.software.flashcardquiz.ui.blockbuster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.atria.software.flashcardquiz.R
import com.atria.software.flashcardquiz.databinding.FragmentConfirmQuizBinding
import com.atria.software.flashcardquiz.ui.blockbuster.helper.reportString


class ConfirmQuizFragment : Fragment() {

    private lateinit var binding : FragmentConfirmQuizBinding
    private var report : helper.Report? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding  = FragmentConfirmQuizBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(report == null) {
            report = arguments?.getSerializable(reportString) as helper.Report?
        }

        binding.subjectNameTextView.text = report?.subjectName
        binding.questionsCountTextView.text = "Questions : " + report?.totalQuestionSize


        binding.startButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable(reportString,report)
            findNavController().navigate(R.id.action_confirmQuizFragment_to_quizFragment,bundle)
        }

    }



}