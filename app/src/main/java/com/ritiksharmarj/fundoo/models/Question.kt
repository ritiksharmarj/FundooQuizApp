package com.ritiksharmarj.fundoo.models

data class Question(
    val description: String = "",
    val optionOne: String = "",
    val optionTwo: String = "",
    val optionThree: String = "",
    val optionFour: String = "",
    val answer: String = "",
    val userAnswer: String = ""
)
