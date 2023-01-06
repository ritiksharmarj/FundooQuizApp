package com.ritiksharmarj.fundoo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ritiksharmarj.fundoo.R
import com.ritiksharmarj.fundoo.models.Quiz
import com.ritiksharmarj.fundoo.utils.IconPicker

class QuizAdapter(private val context: Context, private val quizzes: List<Quiz>) :
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var quizTitle: TextView = itemView.findViewById(R.id.quizTitle)
        var quizIcon: ImageView = itemView.findViewById(R.id.quizIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.quiz_item, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.quizTitle.text = quizzes[position].title
        holder.quizIcon.setImageResource(IconPicker.getIcon())
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

}