package com.example.coffeepod

import android.content.Context
import android.media.Rating
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ReviewAdapter(private val context: Context, val reviewText: MutableList<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvReview: TextView
        val ivUserPhoto: ImageView
        val shopRating: RatingBar


        init {
            tvReview = itemView.findViewById(R.id.tvReview)
            ivUserPhoto = itemView.findViewById(R.id.ivUserPhoto)
            shopRating = itemView.findViewById(R.id.shopRating)
        }

        fun bind(review: Review) {
            tvReview.text = review.getReviewText()
            shopRating.rating = review.getRating()?.toFloat()!!
            Glide.with(itemView.context).load(review.getImage()?.url).into(ivUserPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val reviewView = inflater.inflate(R.layout.item_review, parent, false)
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