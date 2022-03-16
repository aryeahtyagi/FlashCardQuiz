package com.atria.software.flashcardquiz

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atria.software.flashcardquiz.cache.OfflineStorage
import com.atria.software.flashcardquiz.cache.OfflineStorage.homeFeedBack
import com.atria.software.flashcardquiz.cache.OfflineStorage.isUserLoggedIn
import com.atria.software.flashcardquiz.ui.blockbuster.adapter.SubjectAdapter
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    lateinit var sharedpref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFeedBack=true
        isUserLoggedIn=true



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProfile(true)

        val subjectRecyclerView = view.findViewById<RecyclerView>(R.id.subjectRecyclerView)
        subjectRecyclerView.layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)

        getSubjects {
            subjectRecyclerView.adapter = SubjectAdapter(it,findNavController())
        }

    }

    private fun setProfile(b: Boolean) {
        OfflineStorage.setProfileData(requireContext(), b)
    }

    fun getSubjects(onSubjectFetched : (List<String>)->Unit){
        val firebase = FirebaseFirestore.getInstance()
        firebase.collection("Subjects")
            .get()
            .addOnSuccessListener {
                val documents : ArrayList<String> = ArrayList()
                it.documents.forEach { snap->
                    documents.add(snap.id)
                }
                onSubjectFetched(documents)
            }
    }

    override fun onStop() {
        super.onStop()
        homeFeedBack=false
    }

    override fun onResume() {
        super.onResume()
        homeFeedBack=true
    }



}