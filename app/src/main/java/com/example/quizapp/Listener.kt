package com.example.quizapp

import androidx.fragment.app.Fragment

interface Listener {
    fun splashscreen()
    fun startQuiz()
    fun SetQuestion(position:Int)
    fun Summary_Screen()
    fun RulesScreen()
}