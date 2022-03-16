package com.atria.software.flashcardquiz.ui.blockbuster.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.atria.software.flashcardquiz.R
import com.atria.software.flashcardquiz.ui.blockbuster.helper
import com.atria.software.flashcardquiz.ui.blockbuster.helper.reportString
import com.atria.software.flashcardquiz.ui.flightmaster.SubjectFragment
import com.google.firebase.firestore.FirebaseFirestore

class TopicsAdapter(
    val topics: List<SubjectFragment.Topic>,
    val subject: String,
    val navController: NavController
) : RecyclerView.Adapter<TopicsAdapter.TopicsViewHolder>() {

    class TopicsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicTextView = view.findViewById<TextView>(R.id.topicNameTextView)
        val questionTextView = view.findViewById<TextView>(R.id.questionTextView)
        val topicStartButton = view.findViewById<LinearLayout>(R.id.topicStartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic_cards, parent, false)
        return TopicsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicsViewHolder, position: Int) {
        holder.topicTextView.text = topics[position].name
        holder.questionTextView.text = "Questions:" + topics[position].questions
        holder.topicStartButton.setOnClickListener {
            topics[position].name.getQuestions {
                val bundle = Bundle()
                bundle.putSerializable(reportString, it)
                navController.navigate(
                    R.id.action_subjectFragment_to_confirmQuizFragment,
                    bundle
                )
            }
        }
    }

    fun String.getQuestions(onQuestionsFetched: (helper.Report) -> Unit) {
        val firebase = FirebaseFirestore.getInstance()
        firebase.collection("Subjects")
            .document(subject)
            .collection("Topics")
            .document(this)
            .collection("Questions")
            .get()
            .addOnSuccessListener {
                val questions = it.toObjects(SubjectFragment.Question::class.java)
                val pair = ArrayList<Pair<String, String>>()
                questions.forEach {
                    pair.add(Pair(it.question, it.answers))
                }
                val report = helper.Report(
                    subjectName = this,
                    wrongAnswersPair = pair,
                    totalQuestionSize = pair.size
                )
                onQuestionsFetched(report)
            }
    }

    override fun getItemCount(): Int {
        return topics.size
    }

}