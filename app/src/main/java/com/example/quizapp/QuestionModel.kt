package com.example.quizapp

import java.io.Serializable

data class QuestionModel(
    val question:String,
    val options:ArrayList<String>,
    val answer:Int,
    var answerStatus:Boolean=true,
    var bookmark:Boolean=true,
    var ChoosenOption:Int=0
) : Serializable
