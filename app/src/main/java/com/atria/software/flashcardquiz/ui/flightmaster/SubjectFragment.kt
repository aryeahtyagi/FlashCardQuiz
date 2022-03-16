package com.atria.software.flashcardquiz.ui.flightmaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atria.software.flashcardquiz.R
import com.atria.software.flashcardquiz.ui.blockbuster.adapter.TopicsAdapter
import com.atria.software.flashcardquiz.ui.blockbuster.helper
import com.atria.software.flashcardquiz.ui.blockbuster.helper.reportString
import com.google.firebase.firestore.FirebaseFirestore


class SubjectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val extras = arguments?.getString(reportString) ?:"hindi"


        val subjectNameTextView = view.findViewById<TextView>(R.id.subjectNameTextView)
        subjectNameTextView.text = extras
        val topicsRecyclerView = view.findViewById<RecyclerView>(R.id.topicsRecyclerView)
        topicsRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        extras.getTopics {
            topicsRecyclerView.adapter = TopicsAdapter(it,extras,findNavController())
        }

    }

    data class Topic(
        val name :String = "",
        val questions :Long = 0
    )

    fun String.getTopics(onTopicsFetched:((List<Topic>)->Unit)){
        val firebase = FirebaseFirestore.getInstance()
        firebase.collection("Subjects")
            .document(this)
            .collection("Topics")
            .get()
            .addOnSuccessListener {
                val topic = it.toObjects(Topic::class.java)
                onTopicsFetched(topic)
            }
    }

    data class Question(
        val question : String="",
        val answers : String=""
    )



}