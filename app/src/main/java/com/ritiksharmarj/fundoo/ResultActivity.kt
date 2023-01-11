package com.ritiksharmarj.fundoo

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode
import com.google.gson.Gson
import com.ritiksharmarj.fundoo.databinding.ActivityResultBinding
import com.ritiksharmarj.fundoo.models.Quiz

class ResultActivity : AppCompatActivity() {

    lateinit var quiz: Quiz
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson(quizData, Quiz::class.java)

        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value

            val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                builder.append("<font color='#D0BCFF'><b>Question: ${question.description}</b></font><br/><br/>")
                builder.append("<font color='#FFFFFF'>Answer: ${question.answer}</font><br/><br/>")
            } else {
                builder.append("<font color='#6750A4'><b>Question: ${question.description}</b></font><br/><br/>")
                builder.append("<font color='#1C1B1F'>Answer: ${question.answer}</font><br/><br/>")
            }
        }

        binding.tvAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT)
    }

    private fun calculateScore() {
        var score = 0
        for (entry in quiz.questions.entries) {
            val question = entry.value
            if (question.answer == question.userAnswer) {
                score += 10
            }
        }

        binding.tvScore.text = "Your Score: $score"

    }
}