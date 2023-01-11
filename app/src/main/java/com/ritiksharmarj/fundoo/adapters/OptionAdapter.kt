package com.ritiksharmarj.fundoo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ritiksharmarj.fundoo.R
import com.ritiksharmarj.fundoo.models.Question

class OptionAdapter(private val context: Context, private val question: Question) :
    RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    private var options: List<String> =
        listOf(question.option1, question.option2, question.option3, question.option4)

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var quizOption: TextView = itemView.findViewById(R.id.tvQuizOption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.option_item, parent, false)
        return OptionViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.quizOption.text = options[position]
        holder.itemView.setOnClickListener {
            // Store selected option/answer to the user's answer
            question.userAnswer = options[position]
            notifyDataSetChanged()
        }

        // If-Else condition to change card's color for selected option
        if (question.userAnswer == options[position]) {
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg)
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }
}