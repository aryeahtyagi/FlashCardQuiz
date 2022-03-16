package com.atria.software.flashcardquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getDataFromFirebase(){
        val firebase = FirebaseFirestore.getInstance()
        firebase.collection("Subjects")
            .get()
            .addOnSuccessListener {
                val data = it.documents
            }
    }
}