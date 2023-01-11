package com.ritiksharmarj.fundoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.ritiksharmarj.fundoo.adapters.OptionAdapter
import com.ritiksharmarj.fundoo.databinding.ActivityQuestionBinding
import com.ritiksharmarj.fundoo.models.Question
import com.ritiksharmarj.fundoo.models.Quiz

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private lateinit var adapter: OptionAdapter
    var quizzes: MutableList<Quiz>? = null
    var questions: MutableMap<String, Question>? = null
    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpFireStore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        binding.btnPrevious.setOnClickListener {
            index--
            bindViews()
        }

        binding.btnNext.setOnClickListener {
            index++
            bindViews()
        }

        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
        }
    }

    private fun setUpFireStore() {
        val firestore = FirebaseFirestore.getInstance()

        val date = intent.getStringExtra("DATE")

        if (date != null) {
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {
        // First hide all the buttons
        binding.btnPrevious.visibility = View.GONE
        binding.btnNext.visibility = View.GONE
        binding.btnSubmit.visibility = View.GONE

        // Conditions for buttons according to indexes
        when (index) {
            1 -> { // first question
                binding.btnNext.visibility = View.VISIBLE
            }
            questions!!.size -> { // Last question
                binding.btnPrevious.visibility = View.VISIBLE
                binding.btnSubmit.visibility = View.VISIBLE
            }
            else -> { // middle state
                binding.btnNext.visibility = View.VISIBLE
                binding.btnPrevious.visibility = View.VISIBLE
            }
        }

        val question = questions!!["question$index"]
        question?.let {
            binding.quizDescription.text = it.description

            adapter = OptionAdapter(this, it)
            binding.quizOptionList.adapter = adapter
            binding.quizOptionList.layoutManager = LinearLayoutManager(this)
            // Increase performance of recycler view because we've fix size for option is 4
            binding.quizOptionList.setHasFixedSize(true)
        }
    }
}