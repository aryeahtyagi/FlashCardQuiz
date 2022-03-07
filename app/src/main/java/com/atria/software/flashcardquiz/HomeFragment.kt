package com.atria.software.flashcardquiz

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atria.software.flashcardquiz.cache.OfflineStorage.homeFeedBack
import com.atria.software.flashcardquiz.cache.OfflineStorage.isUserLoggedIn

class HomeFragment : Fragment() {
    lateinit var sharedpref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFeedBack=true
        isUserLoggedIn=true
        Log.i("TAGHomefeed", "onCreate: ")



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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