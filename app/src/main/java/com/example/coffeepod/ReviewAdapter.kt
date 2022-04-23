package com.example.coffeepod

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private val context: Context,  val reviewText: MutableList<String>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rcReviewText = itemView.findViewById<TextView>(R.id.rcReviewText)
        fun bind(review: String) {
            rcReviewText.text = review
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val reviewView = inflater.inflate(R.layout.item_review_text, parent, false)
        return ViewHolder(reviewView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviewText[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int {
        return reviewText.size
    }


}