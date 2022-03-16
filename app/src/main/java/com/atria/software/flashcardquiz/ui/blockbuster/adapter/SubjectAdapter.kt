package com.atria.software.flashcardquiz.ui.blockbuster.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.atria.software.flashcardquiz.R
import com.atria.software.flashcardquiz.ui.blockbuster.helper

class SubjectAdapter(
    val id:List<String>,
    val navController: NavController
) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val subjectTextView = view.findViewById<TextView>(R.id.subjectNameTextView)
        val subjectStartBtn = view.findViewById<CardView>(R.id.subjectStartButton)
        val carView = view.findViewById<ImageView>(R.id.itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subject_card,parent,false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        if(position %2 == 1)
        {
            holder.carView.setImageResource(R.drawable.bluecard)
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.carView.setImageResource(R.drawable.red_bg);
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
        holder.subjectTextView.text = id[position]
        holder.subjectStartBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(helper.reportString, id[position])
            navController.navigate(
                R.id.action_homeFragment_to_subjectFragment,
                bundle
            )
        }
    }

    override fun getItemCount(): Int {
        return id.size
    }

}

