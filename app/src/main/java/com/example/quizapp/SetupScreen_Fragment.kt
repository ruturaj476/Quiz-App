package com.example.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class SetupScreen_Fragment : Fragment() {
    private lateinit var start_btn:Button
    private lateinit var listener: Listener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_setup_screen_, container, false)
        start_btn=view.findViewById(R.id.startButton)
        listener=activity as Listener
        start_btn.setOnClickListener{
            listener.startQuiz()
        }
        return view
    }
}